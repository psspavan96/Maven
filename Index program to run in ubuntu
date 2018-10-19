package lucene;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.util.List;

import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class index{
	static List<Document> docu;
	public static String bm25_indpath = "bm25_index";
	public static String vector_indpath = "vector_index";
	public static String default_indpath = "default_index";
	public static String docPath = "/home/ubuntu/cran.all.1400";
	public static String queryPath = "/home/ubuntu/cran.qry";
	public static String bm25_resPath = "bm25_result.txt";
	public static String vector_resPath = "vector_result.txt";
	public static String default_resPath = "default_result.txt";
	public static float K = 1.8f, B = 0.75f;
	
	private index() {};
	
	public static void main(String[] args) throws Exception{
		final Path docDir = Paths.get(docPath);
		
		for(int j = 0; j<=2; j++)
		{
			Directory dir = null;
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			if(j == 0) {
				iwc.setSimilarity(new BM25Similarity(K , B));
				dir = FSDirectory.open(Paths.get(bm25_indpath));
				}
			else if(j == 1) {
				iwc.setSimilarity(new ClassicSimilarity());
				dir = FSDirectory.open(Paths.get(vector_indpath));	
			}
			else if( j == 2) {
				dir = FSDirectory.open(Paths.get(default_indpath));
			}
			
			iwc.setOpenMode(OpenMode.CREATE);
			
			docu = new ArrayList<Document>();
			IndexWriter writer = new IndexWriter(dir, iwc);
			parser(writer, docDir, docPath);
			writer.addDocuments(docu);
			writer.close();
		}
		
		search.search();
	}
		
	static void parser(IndexWriter writer, Path file, String docPath) throws IOException{
	
		BufferedReader inputStream = new BufferedReader(new FileReader(new File(docPath)));
		String line = null;
		String key = null;
		Map<String,String> map = null;
		while((line = inputStream.readLine())!=null) {
			if(line.matches(".[ITABW].*")) {
				if (line.matches(".I.*")) {
					if(map!= null) {
						indexDoc(writer, file, map);
					}
					map = new HashMap<>();
					map.put("I", line.split(" ")[1]);
				}
				else if(line.matches(".T.*"))key = "T";
				else if(line.matches(".A.*"))key = "A";
				else if(line.matches(".B.*"))key = "B";
				else if(line.matches(".W.*"))key = "W";
				continue;
			}
		
			else {
				String data = (map.get(key) == null)?line:map.get(key)+" "+line;
				map.put(key, data);}}
				indexDoc(writer, file, map);
					
	}
	static void indexDoc(IndexWriter writer, Path file, Map<String, String> map) throws IOException{
			Document doc = new Document();
			FieldType ft = new FieldType(TextField.TYPE_STORED);
			ft.setStoreTermVectors(true);
			Field id = new StringField("id", map.get("I"), Field.Store.YES);
			
			doc.add(id);
			
			if(map.get("I")!= null) {
				Field title = new Field("contents", map.get("I") + " " + map.get("T"), ft);
				doc.add(title);
			}
			if(map.get("A")!= null) {
				Field authors = new Field("contents", map.get("A") + " " + map.get("T"), ft);
				doc.add(authors);
			}
			if(map.get("B")!= null) {
				Field bib = new Field("contents", map.get("B") + " " + map.get("T"), ft);
				doc.add(bib);
			}
			if(map.get("W")!= null) {
				Field word = new Field("contents", map.get("W") + " " + map.get("T"), ft);
				doc.add(word);
			}
			
			docu.add(doc);		
		}
		
	}
	
