package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private Marche marche ;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum , int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche (nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	public String installerVendeur (Gaulois vendeur , String produit , int nbProduit)
	{
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + produit ) ;
		int emplacement = marche.trouverEtalLibre() ;
		if (emplacement == - 1 ) { chaine.append("Il n'y a plus d'étal libre pour" + vendeur.getNom()  ) ;}
		else 
		{
			marche.utiliserEtal(emplacement, vendeur, produit, nbProduit);
			emplacement+=1;
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal " + emplacement ) ;
		}
		return chaine.toString() ;
	}
	
	private static class Marche {
		private Etal [] etals ;
		
		private Marche (int nbEtals)
		{
			this.etals= new Etal [nbEtals];
		}
		
		public void utiliserEtal(int indiceEtal , Gaulois vendeur , String produit , int nbProduit)
		{
			Etal etal = etals[indiceEtal] ;
			if (! etal.isEtalOccupe())
			{
				etal.occuperEtal(vendeur, produit, nbProduit);
			}
		}
		public int trouverEtalLibre ()
		{
			int compteur = 0;
			for (Etal etal : etals) {
			if (!etal.isEtalOccupe())
			{
				return compteur ;
			}
			else {compteur +=1 ;}
			}
			return -1 ;
		}
		
		public Etal[] trouverEtals(String produit)
		{
			int nbetals=0;
			for (Etal etal : etals)
			{
				if (etal.contientProduit(produit)) { nbetals += 1 ; }
			}
			Etal [] tabEtal = new Etal [nbetals] ;
			int compt = 0 ;
			for (Etal etal : etals)
			{
				if (etal.contientProduit(produit)) 
				{   
					tabEtal [compt] = etal ;
					compt += 1 ;
				}
			}
			return tabEtal ;
		}
		public Etal trouverVendeur (Gaulois gaulois)
		{
			for (Etal etal : etals)
			{
				if (etal.getVendeur().equals(gaulois))
				{
					return etal ;
				}
			}
			return null ;
		}
		public void afficherMarche() {
			int compteuretalvide=0;
			for (Etal etal : etals)
			{
				if (etal.isEtalOccupe()) { etal.afficherEtal(); }
				else { compteuretalvide+=1 ; }
			}
			System.out.println("Il reste " + compteuretalvide + " étals non utilisés dans le marché.") ; 
		}
	}
	
}