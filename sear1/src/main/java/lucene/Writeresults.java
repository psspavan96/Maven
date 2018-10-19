package lucene;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
public class Writeresults
{
static BufferedWriter outputStream = null;
static String resultspath = "";
public static void writeresults(int query, ScoreDoc[] scoreDocs, IndexSearcher searcher, int j) throws Exception {

	if(j ==0) { resultspath = index.bm25_resPath;}
	else if (j == 1) { resultspath = index.vector_resPath;}
	else if (j == 2) { resultspath = index.default_resPath;}
	
outputStream = new BufferedWriter(new FileWriter(new File(resultspath),true));
if (query==1)
outputStream = new BufferedWriter(new FileWriter(new File(resultspath)));
String line = "";

for(int i = 0; i<scoreDocs.length;i++) {
Document hitdoc = searcher.doc(scoreDocs[i].doc);
line+=query+" Q0"+" "+(hitdoc.get("id"))+" 0"+" "+scoreDocs[i].score+" STANDARD"+"\n";
//System.out.println(line);
}
//System.out.println(line);
line.substring(line.length()-1 , line.length());
outputStream.write(line);
outputStream.close();
}
}
