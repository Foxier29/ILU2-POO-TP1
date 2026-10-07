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
			chaine.append("Il n'y a encore aucun habitant au village du chef ");
			chaine.append(chef.getNom() );
			chaine.append(".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " );
				chaine.append(villageois[i].getNom() );
				chaine.append("\n");
			}
		}
		return chaine.toString();
	}
	public String installerVendeur (Gaulois vendeur , String produit , int nbProduit)
	{
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre ") ;
		chaine.append( nbProduit );
		chaine.append(" ");
		chaine.append(produit );
		chaine.append(" \n");
		int emplacement = marche.trouverEtalLibre() ;
		if (emplacement == - 1 ) 
		{ chaine.append(" Il n'y a plus d'étal libre pour ");
			chaine.append(vendeur.getNom()  );}
		else 
		{
			marche.utiliserEtal(emplacement, vendeur, produit, nbProduit);
			emplacement+=1;
			chaine.append("Le vendeur ") ;
			chaine.append(vendeur.getNom());
			chaine.append(" vend des ");
			chaine.append(produit); 
			chaine.append(" à l'étal ") ;
			chaine.append(emplacement ) ;
		}
		return chaine.toString() ;
	}
	
	public String rechercherVendeursProduit(String produit)
	{
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		if (etals.length==0)
		{
			chaine.append("Il n'y a pas de vendeur qui propose des ");
			chaine.append(produit);
			chaine.append(" au marché.");

		}
		else if (etals.length==1)
		{
			chaine.append("Seul le vendeur ");
			Gaulois vendeur = etals[0].getVendeur();
			chaine.append(vendeur.getNom()); 
			chaine.append(" propose des ");
			chaine.append(produit);
			chaine.append(" au marché");
		}
		else 
		{
			chaine.append("les vendeurs qui proposent des ");
			chaine.append(produit);
			chaine.append(" sont : \n");
			for(Etal etal : etals)	
			{
				chaine.append("- ");
				Gaulois vendeur = etal.getVendeur();
				chaine.append(vendeur.getNom());
				chaine.append("\n");
			}
			
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur)
	{
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur)
	{
		return rechercherEtal(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder("Le marché du village \"");
		chaine.append(nom);
		chaine.append(" \" possède plusieurs étals : \n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
		
	
	}
	private static class Marche {
		private Etal [] etals ;
		private int nbEtals=0;
		
		private Marche (int nbEtals)
		{
			this.etals= new Etal [nbEtals];
			for (int i=0;i<nbEtals;i++)
			{
				etals[i]=new Etal();
			}
			this.nbEtals=nbEtals;
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
			for (int i=0;i<nbEtals;i++)
		
			{
				if (etals[i].contientProduit(produit)) { nbetals += 1 ; }
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
		public String afficherMarche() {
			int compteuretalvide=0;
			StringBuilder chaine = new StringBuilder();
			for (Etal etal : etals)
			{
				if (etal.isEtalOccupe()) { chaine.append(etal.afficherEtal()); }
				else { compteuretalvide+=1 ; }
			}
			chaine.append("Il reste ");
			chaine.append(compteuretalvide);
			chaine.append(" étals non utilisés dans le marché. \n");
			return chaine.toString();
		}
	}
	
}