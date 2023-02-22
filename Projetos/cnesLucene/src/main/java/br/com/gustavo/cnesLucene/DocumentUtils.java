package br.com.gustavo.cnesLucene;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // Configure the ObjectMapper to pretty-print the JSON output
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String documentToJsonString(Document doc) {
        try {
            Map<String, Object> map = new HashMap<>();
            for (IndexableField field : doc.getFields()) {
                map.put(field.name(), field.stringValue());
            }
            return mapper.writeValueAsString(map);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize Document to JSON", e);
        }
    }
    
    public static List<String> mapMultipleDocuments(List<Document> documents) throws RuntimeException{
    	List<String> jsonList = new ArrayList<>();
    	for(Document document : documents) {
			jsonList.add(DocumentUtils.documentToJsonString(document).replaceAll("\"", "").replaceAll("\\\\", "").replaceAll("[\\r\\n]+", ""));
    	}
    	return jsonList;
    }
}
