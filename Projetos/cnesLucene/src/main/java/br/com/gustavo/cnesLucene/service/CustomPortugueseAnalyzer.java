package br.com.gustavo.cnesLucene.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.CharArraySet;

import java.util.Arrays;

public class CustomPortugueseAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        // Tokenizador padrão
        StandardTokenizer tokenizer = new StandardTokenizer();

        // Stopwords em português (pode expandir conforme necessário)
        CharArraySet stopWords = new CharArraySet(
                Arrays.asList("de", "da", "do", "em", "para", "com", "sem", "um", "uma", "os", "as"),
                true
        );

        // Pipeline de filtros
        TokenStream tokenStream = new LowerCaseFilter(tokenizer);          // tudo minúsculo
        tokenStream = new ASCIIFoldingFilter(tokenStream);                 // remove acentos
        tokenStream = new StopFilter(tokenStream, stopWords);              // remove stopwords

        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}

