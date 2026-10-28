package br.com.gustavo.cnesLucene.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

@Service
public class Search {

	private static final Analyzer analyzer = new PortugueseAnalyzer();

	public static List<Document> searchFiles(String queryString, String field, String indexLocation) {
		Query query;
		IndexReader indexReader = null;
		try {
			query = new QueryParser(field, analyzer).parse(queryString);

			Directory indexDirectory = FSDirectory.open(Paths.get(indexLocation));
			indexReader = DirectoryReader.open(indexDirectory);
			IndexSearcher searcher = new IndexSearcher(indexReader);

			TopScoreDocCollector collector = TopScoreDocCollector.create(20, Integer.MAX_VALUE);
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			List<Document> documents = new ArrayList<>();

			for (int i = 0; i < hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				documents.add(d);
			}

			return documents;
		} catch (ParseException | IOException e) {
			System.err.println("Erro na busca: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (indexReader != null) indexReader.close();
			} catch (IOException e) {
				System.err.println("Erro ao fechar IndexReader: " + e.getMessage());
			}
		}
		return new ArrayList<>();
	}

	public static List<Document> searchFilesPaginated(
			String queryString,
			String field,
			String indexLocation,
			int page,
			int pageSize,
			String sortType) {
		try {
			Query query = new QueryParser(field, analyzer).parse(queryString);

			Directory indexDirectory = FSDirectory.open(Paths.get(indexLocation));
			try (IndexReader indexReader = DirectoryReader.open(indexDirectory)) {
				IndexSearcher searcher = new IndexSearcher(indexReader);

				Sort sort;
				if ("alpha".equalsIgnoreCase(sortType)) {
					sort = new Sort(new SortField(field, SortField.Type.STRING, false)); // A→Z
				} else {
					sort = Sort.RELEVANCE;
				}

				int start = (page - 1) * pageSize;
				TopDocs topDocs = searcher.search(query, start + pageSize, sort);

				List<Document> documents = new ArrayList<>();
				ScoreDoc[] hits = topDocs.scoreDocs;

				for (int i = start; i < Math.min(start + pageSize, hits.length); i++) {
					documents.add(searcher.doc(hits[i].doc));
				}

				return documents;
			}
		} catch (ParseException | IOException e) {
			System.err.println("Erro na busca: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


	public static List<Document> searchFilesScroll(String queryString, String field, String indexLocation, ScoreDoc lastDoc, int pageSize) {
		try {
			Query query = new QueryParser(field, analyzer).parse(queryString);

			Directory indexDirectory = FSDirectory.open(Paths.get(indexLocation));
			try (IndexReader indexReader = DirectoryReader.open(indexDirectory)) {
				IndexSearcher searcher = new IndexSearcher(indexReader);

				// Se lastDoc for null, começa do início
				TopDocs topDocs = searcher.searchAfter(lastDoc, query, pageSize);

				List<Document> documents = new ArrayList<>();
				for (ScoreDoc sd : topDocs.scoreDocs) {
					documents.add(searcher.doc(sd.doc));
				}

				return documents;
			}
		} catch (ParseException | IOException e) {
			System.err.println("Erro na busca: " + e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<>();
	}




	public static List<Document> searchMultipleQueries(List<String> queries, List<String> fields,
			String indexTypeLocation) {
		try {
			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			for (String query : queries) {
				BooleanQuery.Builder subBuilder = new BooleanQuery.Builder();
				for (String field : fields) {
					QueryParser parser = new QueryParser(field, new StandardAnalyzer());
					Query subQuery = parser.parse(query);
					subBuilder.add(subQuery, BooleanClause.Occur.SHOULD);
				}
				builder.add(subBuilder.build(), BooleanClause.Occur.MUST);
			}
			BooleanQuery query = builder.build();

			Directory indexDirectory = FSDirectory.open(Paths.get(indexTypeLocation));
			IndexReader indexReader = DirectoryReader.open(indexDirectory);
			IndexSearcher searcher = new IndexSearcher(indexReader);

			TopScoreDocCollector collector = TopScoreDocCollector.create(20, 20);
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			List<Document> documents = new ArrayList<>();

			for (int i = 0; i < hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				documents.add(d);
			}

			return documents;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<Document> searchByFields(Map<String, String> fields, String indexLocation) {
		try {
			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			for (Map.Entry<String, String> entry : fields.entrySet()) {
				Query query = new QueryParser(entry.getKey(), analyzer).parse(entry.getValue());
				builder.add(query, BooleanClause.Occur.MUST);
			}

			try (IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexLocation)))) {
				IndexSearcher searcher = new IndexSearcher(indexReader);
				TopDocs topDocs = searcher.search(builder.build(), 20);
				List<Document> documents = new ArrayList<>();
				for (ScoreDoc hit : topDocs.scoreDocs) {
					documents.add(searcher.doc(hit.doc));
				}
				return documents;
			}
		} catch (ParseException | IOException e) {
			System.err.println("Erro na busca por campos: " + e.getMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

}
