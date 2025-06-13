package com.sidoCop.sysPharma.domaine.model;

// Import statique pour les assertions
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MedicamentTest {

	private Medicament medicament;
	private Categorie categorie;

	// Cette méthode s'exécute avant chaque méthode de test
	@BeforeEach
	void setUp() {
		System.out.println("Exécution de setUp() avant un test...");
		categorie = new Categorie("Douleur");
		medicament = new Medicament(1, "Paracétamol", 2.50, "Antidouleur efficace", "paracetamol.png", categorie);
	}

	@Test // Indique que c'est une méthode de test
	@DisplayName("Test de la création d'un médicament avec toutes les propriétés") // Nom plus lisible pour le test
	void testMedicamentCreationFull() {
		System.out.println("Exécution de testMedicamentCreationFull()");
		// Vérifier que l'objet n'est pas nul
		assertNotNull(medicament, "Le médicament ne devrait pas être nul");
		// Vérifier les propriétés
		assertEquals(1, medicament.getId(), "L'ID du médicament doit être 1");
		assertEquals("Paracétamol", medicament.getDesignation(), "La désignation doit être 'Paracétamol'");
		assertEquals(2.50, medicament.getPrix(), 0.001, "Le prix doit être 2.50"); // Delta pour les doubles
		assertEquals("Antidouleur efficace", medicament.getDescription(), "La description doit correspondre");
		assertEquals("paracetamol.png", medicament.getImage(), "L'image doit correspondre");
		assertNotNull(medicament.getCategorie(), "La catégorie ne doit pas être nulle");
		assertEquals("Douleur", medicament.getCategorie().getDesignation(),
				"La désignation de catégorie doit être 'Douleur'");
	}

	@Test
	@DisplayName("Test de la mise à jour du prix d'un médicament")
	void testSetPrix() {
		System.out.println("Exécution de testSetPrix()");
		double nouveauPrix = 3.00;
		medicament.setPrix(nouveauPrix);
		assertEquals(nouveauPrix, medicament.getPrix(), 0.001, "Le prix doit être mis à jour");
	}

	@Test
	@DisplayName("Test du constructeur simplifié sans image et catégorie")
	void testMedicamentCreationSimplified() {
		System.out.println("Exécution de testMedicamentCreationSimplified()");
		Medicament simplifiedMedicament = new Medicament(2, "Aspirine", 1.80, "Anti-inflammatoire");
		assertNotNull(simplifiedMedicament);
		assertEquals(2, simplifiedMedicament.getId());
		assertEquals("Aspirine", simplifiedMedicament.getDesignation());
		assertEquals(1.80, simplifiedMedicament.getPrix(), 0.001);
		assertEquals("Anti-inflammatoire", simplifiedMedicament.getDescription());
		assertNull(simplifiedMedicament.getImage(), "L'image doit être nulle pour ce constructeur");
		assertNull(simplifiedMedicament.getCategorie(), "La catégorie doit être nulle pour ce constructeur");
	}
}