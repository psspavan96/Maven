package lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;

public class search {

    static IndexReader reader;
    static IndexSearcher searcher =null;
    static Analyzer analyzer =null;
    static String Index = "/Users/pavanpss/Desktop/Index";
    static String inpquery = "/Users/pavanpss/Desktop/cran/cran.qry";
    private search() {}
    
    public static void search() throws Exception {
    	for(int j = 0; j<=2; j++)
		{
        String field = "contents";
        String queries = null;
        String queryString = null;
        if(j == 0) {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(index.bm25_indpath)));
			searcher = new IndexSearcher(reader);
			searcher.setSimilarity(new BM25Similarity(index.K, index.B));
			}
		else if(j == 1) {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(index.vector_indpath)));
			searcher = new IndexSearcher(reader);
			searcher.setSimilarity(new ClassicSimilarity());	
		}
		else if( j == 2) {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(index.default_indpath)));
			searcher = new IndexSearcher(reader);
		}
        
        analyzer = new StandardAnalyzer();
        BufferedReader inputStream = new BufferedReader(new FileReader(new File(index.queryPath)));
        QueryParser parser= new QueryParser(field, analyzer);
        List<String> queryList = generateQueryList(inputStream);
        
        int i = 1;
        for(String k: queryList) {
        	Query query = parser.parse(k);
        	ScoreDoc scoreDocs[] = searcher.search(query, 1400).scoreDocs;
        	Writeresults.writeresults(i,scoreDocs,searcher,j);
        	i++;
        	
        }
        reader.close();
        
		}
   }
private static List<String> generateQueryList(BufferedReader inputStream) throws IOException {
	String q = "";
	String line = null;
	List<String> queryList = new ArrayList<>();
	while((line=inputStream.readLine())!=null) {
		if(line.matches(".I.*")) {
			if(q.equals("")) continue;
			q = q.replaceAll("\\?", "").replaceAll("\\?", "");
			queryList.add(q);
			
		}else if(line.matches(".W.*")) {
			q="";
		}
		else {
			q = (q.equals("")?q+line: q+" "+line);
			
		}
	}
	q = q.replaceAll("\\?", "").replaceAll("\\?", "");
	queryList.add(q);
	return queryList;
}

}
