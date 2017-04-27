package ch.unibe.scg.metrics.sourcemetrics.domain;

import com.github.mauricioaniche.ck.CKNumber;

public class SMFile {

	private String file;
	private String className;
	private String type;

	private int dit;
	private int noc;
	private int wmc;
	private int cbo;
	private int lcom;
	private int rfc;
	private int nom;
	private int nopm;
	private int nosm;

	private int nof;
	private int nopf;
	private int nosf;

	private int nosi;
	private int loc;
	
	// private Logger logger = Logger.getLogger(CMFile.class);

	public SMFile() {}
	
	public SMFile(String file) {
		this.file = file;
	}
	
	public SMFile(CKNumber ck) {
		file = ck.getFile();
		className = ck.getClassName();
		type = ck.getType();
		cbo = ck.getCbo();
		wmc = ck.getWmc();
		dit = ck.getDit();
		noc = ck.getNoc();
		rfc = ck.getRfc();
		lcom = ck.getLcom();
		nom = ck.getNom();
		nopm = ck.getNopm(); 
		nosm = ck.getNosm();
		nof = ck.getNof();
		nopf = ck.getNopf(); 
		nosf = ck.getNosf();
		nosi = ck.getNosi();
		loc = ck.getLoc();
	}
	
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDit() {
		return dit;
	}

	public void setDit(int dit) {
		this.dit = dit;
	}

	public int getNoc() {
		return noc;
	}

	public void setNoc(int noc) {
		this.noc = noc;
	}

	public int getWmc() {
		return wmc;
	}

	public void setWmc(int wmc) {
		this.wmc = wmc;
	}

	public int getCbo() {
		return cbo;
	}

	public void setCbo(int cbo) {
		this.cbo = cbo;
	}

	public int getLcom() {
		return lcom;
	}

	public void setLcom(int lcom) {
		this.lcom = lcom;
	}

	public int getRfc() {
		return rfc;
	}

	public void setRfc(int rfc) {
		this.rfc = rfc;
	}

	public int getNom() {
		return nom;
	}

	public void setNom(int nom) {
		this.nom = nom;
	}

	public int getNopm() {
		return nopm;
	}

	public void setNopm(int nopm) {
		this.nopm = nopm;
	}

	public int getNosm() {
		return nosm;
	}

	public void setNosm(int nosm) {
		this.nosm = nosm;
	}

	public int getNof() {
		return nof;
	}

	public void setNof(int nof) {
		this.nof = nof;
	}

	public int getNopf() {
		return nopf;
	}

	public void setNopf(int nopf) {
		this.nopf = nopf;
	}

	public int getNosf() {
		return nosf;
	}

	public void setNosf(int nosf) {
		this.nosf = nosf;
	}

	public int getNosi() {
		return nosi;
	}

	public void setNosi(int nosi) {
		this.nosi = nosi;
	}

	public int getLoc() {
		return loc;
	}

	public void setLoc(int loc) {
		this.loc = loc;
	}

	public String toString() {
		return "SZZFile ["+file+", cbo: "+cbo+", dit: "+dit+", noc: " + noc + ", nof: " + nof + ", nopf: " + nopf + ", nosf: " + nosf + ", nom: " + nom + ", nopm: " + nopm + ", nosm: " + nosm
				+ ", nosi: " + nosi + ", rfc: " + rfc + ", wmc: " + wmc + ", loc: " + loc + ", lcom: " + lcom;
	}
}
