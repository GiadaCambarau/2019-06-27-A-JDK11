package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private EventsDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<Integer> anni;
	private List<String> categorie;
	private List<Arco> archi;
	private List<String> best;
	private int min;
	
	public Model() {
		super();
		this.dao = new EventsDao();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.anni = dao.getAnni();
		this.categorie = dao.getCategories();
		this.archi = new ArrayList<>();
	}
	
	public List<Integer> getAnni(){
		return this.anni;
		
	}
	public List<String> getCate(){
		return this.categorie;
	}
	
	public void creaGrafo(String categotia, int anno) {
		List<String> vertici = dao.getVertici(anno, categotia);
		Graphs.addAllVertices(this.grafo, vertici);
		this.archi = dao.getArchi(anno, categotia);
		for (Arco a : archi) {
			Graphs.addEdgeWithVertices(this.grafo, a.getT1(), a.getT2(), a.getPeso());
		}
	}
	
	public int getV() {
		return this.grafo.vertexSet().size();
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Arco> maxPeso(){
		List<Arco> result = new ArrayList<>();
		int max =0;
		for (Arco a : archi ) {
			if (a.getPeso()>max) {
				max = a.getPeso();
			}
		}
		for (Arco a : archi) {
			if (a.getPeso()== max) {
				result.add(a);
			}
		}
		return result;
	}
	
	public List<String> trovaPercorso(Arco a){
		String partenza = a.getT1();
		String arrivo = a.getT2();
		List<String> parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		this.min = 10000000;
		parziale.add(partenza);
		List<String> tuttiVertici = new ArrayList<>(this.grafo.vertexSet());
		tuttiVertici.remove(partenza);
		ricorsione(partenza, parziale, tuttiVertici, arrivo);
		return this.best;
		
	}

	private void ricorsione(String partenza, List<String> parziale, List<String> tuttiVertici, String arrivo) {
		String corrente = parziale.get(parziale.size()-1);
		//condizione di uscita
		//il vertice di arrivo è quello selezionato.
		//ha attraversato tutti i vertici
		//ha il peso minimo
		if (corrente.compareTo(arrivo)==0 && tuttiVertici.isEmpty()) {
			if (calcolaPeso(parziale)<= min) {
				min = calcolaPeso(parziale);
				this.best = new ArrayList<>(parziale);
			}
			return;
		}
		
		//condizione normale
		List<String> vicini = Graphs.neighborListOf(this.grafo, corrente);
		for (String s : vicini) {
			if (!parziale.contains(s)) {
				parziale.add(s);
				List<String> nuoviRimanenti = new ArrayList<>(tuttiVertici);
				nuoviRimanenti.remove(s);
				ricorsione(s, parziale, nuoviRimanenti, arrivo);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	private int calcolaPeso(List<String> parziale) {
	    int peso = 0;
	    if (parziale.size() <= 1) {
	        return peso;
	    }
	    for (int i = 0; i < parziale.size() - 1; i++) {
	        DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(i), parziale.get(i + 1));
	        if (e != null) {
	            peso += this.grafo.getEdgeWeight(e);
	        }
	    }
	    return peso;
	}

	
	
}
