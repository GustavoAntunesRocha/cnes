package br.com.gustavo.cnesLucene.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

@Service
public class Search {

	private static StandardAnalyzer analyzer = new StandardAnalyzer();

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

}
