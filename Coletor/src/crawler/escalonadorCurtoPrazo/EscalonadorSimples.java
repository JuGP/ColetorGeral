package crawler.escalonadorCurtoPrazo;

import java.util.HashMap;
import java.util.HashSet;

import com.trigonic.jrobotx.Record;

import crawler.Servidor;
import crawler.URLAddress;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EscalonadorSimples implements Escalonador {

    private LinkedHashMap<Servidor, LinkedList<URLAddress>> hashServer = new LinkedHashMap<>();
    private HashMap<Servidor, Record> robotsServidor = new HashMap<>();
    public static final int PROFUNDIDADE = 4;
    public static final int LIMITE_PAGINAS = 500;
    private HashSet<String> descobertos = new HashSet<>();
    public int paginas = 0;

    @Override
    public synchronized URLAddress getURL() {
        //exibe();
        if (!finalizouColeta()) {
            URLAddress url = null;
            Servidor s = null;
            do {
                for (Servidor key : hashServer.keySet()) {
                    LinkedList<URLAddress> value = hashServer.get(key);

                    if (!value.isEmpty() && key.isAccessible()) {
                        s = key;
                        break;
                    }
                }
                if (s != null) {
                    LinkedList<URLAddress> value = hashServer.get(s);
                    url = value.removeFirst();
                    countFetchedPage();
                    s.acessadoAgora();
                    if (hashServer.get(s).isEmpty()) {
                        hashServer.remove(s);
                    }
                    return url;
                }
                try {
                    wait(1000L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(EscalonadorSimples.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (hashServer.isEmpty()) {
                    s = null;
                }
            } while (s == null);
        }

        return null;
    }

    @Override
    public synchronized boolean adicionaNovaPagina(URLAddress urlAdd) {
        LinkedList<URLAddress> aux = new LinkedList<URLAddress>();
        Servidor servidor = new Servidor(urlAdd.getDomain());
        if (descobertos.contains(urlAdd.toString())) {
            return false;
        }
        if (urlAdd.getDepth() >= PROFUNDIDADE) {
            return false;
        }
        if (hashServer.containsKey(servidor)) {
            aux = hashServer.get(servidor);
            descobertos.add(urlAdd.toString());
            aux.add(urlAdd);
        } else {
            descobertos.add(urlAdd.toString());
            aux.add(urlAdd);
            hashServer.put(servidor, aux);
        }
        return true;
    }

    @Override
    public Record getRecordAllowRobots(URLAddress url) {
        Servidor servidor = new Servidor(url.getDomain());
        if (robotsServidor.containsKey(servidor)) {
            return robotsServidor.get(servidor);
        }
        return null;
    }

    @Override
    public synchronized void putRecorded(String domain, Record domainRec) {
        Servidor servidor = new Servidor(domain);
        if (!robotsServidor.containsKey(servidor)) {
            robotsServidor.put(servidor, domainRec);
        }
    }

    @Override
    public boolean finalizouColeta() {

        return (paginas > LIMITE_PAGINAS);
    }

    @Override
    public synchronized void countFetchedPage() {
        paginas++;

    }

    public void exibe() {
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("NUMERO DE PAGINAS COLETADAS: " + paginas);
        System.out.println("FILA DE PAGINAS NÃO COLETADAS:");
        for (Servidor s : hashServer.keySet()) {
            System.out.println("\tServidor " + s.getNome());
            for (URLAddress url : hashServer.get(s)) {
                System.out.println("\t\tURL: " + url.toString() + " PRODUNDIDADE: " + url.getDepth());
            }
        }
        System.out.println("FILA DE PAGINAS DESCOBERTAS:");
        for (String url : descobertos) {
            System.out.println("\t\tURL: " + url.toString());
        }
    }

}
