package crawler;

import crawler.escalonadorCurtoPrazo.EscalonadorSimples;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import org.apache.log4j.BasicConfigurator;

public class Coletor {

    public static void main(String[] args) throws MalformedURLException, UnknownHostException {
        EscalonadorSimples e = new EscalonadorSimples();

        URLAddress url1 = new URLAddress("https://edition.cnn.com/", 1);
        URLAddress url2 = new URLAddress("https://www.openhub.net/", 1);
        URLAddress url3 = new URLAddress("https://uai.com.br/", 1);
        URLAddress url4 = new URLAddress("https://www.msn.com/", 1);
        URLAddress url5 = new URLAddress("https://www.clarin.com/", 1);
        URLAddress url6 = new URLAddress("https://www.lanacion.com.ar/", 1);
        e.adicionaNovaPagina(url1);
        e.adicionaNovaPagina(url2);
        e.adicionaNovaPagina(url3);
        e.adicionaNovaPagina(url4);
        e.adicionaNovaPagina(url5);
        e.adicionaNovaPagina(url6);
        
        int n = 10; // Numero de threads
        for (int i = 0; i < n; i++) {
            Thread object = new Thread(new PageFetcher(e));
            object.start();
        }
    }
}
