package champollion;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;
		
	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");		
	}
	

	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
                        "Un nouvel enseignant doit avoir 0 heures prévues");
	}
	
	@Test
	public void testAjouteHeures() {
                // 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);

		assertEquals(10, untel.heuresPrevuesPourUE(uml),
                        "L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

		// 20h TD pour UML
        untel.ajouteEnseignement(uml, 0, 20, 0);
                
		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
                         "L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");		
		
	}

	@Test
	public void testAPlanifier() {
		untel.ajouteEnseignement(uml, 0, 10, 0);
		Date date = new Date("13/12/2022");
		Intervention inter = new Intervention(date, 1, false, 10);
		//untel.ajouteIntervention(inter);
		assertEquals(0, untel.resteAPlanifier(uml, TypeIntervention.CM), "Erreur, il n'y a pas assez d'heure planifiées");
	}

	@Test
	public void testAjouteHeuresCM() {
		//6 CM pour UML
		untel.ajouteEnseignement(uml, 6, 0, 0);
		//9 car 6*1.5
		assertEquals(9, untel.heuresPrevuesPourUE(uml),
				"L'enseignant doit maintenant avoir 9 heures prévues pour l'UE 'uml'");
	}

	@Test
	public void testAjouteHeuresTP() {
		//16 TP pour java
		untel.ajouteEnseignement(java, 0, 0, 16);
		//12 car 16*0.75
		assertEquals(12, untel.heuresPrevuesPourUE(java),
				"L'enseignant doit maintenant avoir 15 heures prévues pour l'UE 'uml'");
	}

	@Test
	public void testEnSousService() {
		untel.ajouteEnseignement(uml, 0, 11, 0);
        assertTrue(untel.enSousService(), "L'enseignant doit être en sous-service car il n'a pas 192 heures équivalent TD (ici il en a 10)");
		untel.ajouteEnseignement(uml, 0, 187, 0);
        assertFalse(untel.enSousService(), "L'enseignant n'est pas en sous-service car il a au moins 192 heures équivalent TD (ici il en a 192)");
		untel.ajouteEnseignement(uml, 0, 28, 0);
        assertFalse(untel.enSousService(), "L'enseignant n'est pas en sous-service car il a au moins 192 heures équivalent TD (ici il en a 202)");
	}

	@Test
	public void testAjouteEnseignementAvecValeursNegatives() {
		assertThrows(IllegalArgumentException.class, () -> {
			untel.ajouteEnseignement(uml, -5, 10, 0);
		}, "Une exception doit être levée pour un volume d'heures négatif");

		assertThrows(IllegalArgumentException.class, () -> {
			untel.ajouteEnseignement(uml, 10, -5, 0);
		}, "Une exception doit être levée pour un volume d'heures négatif");
	}


	@Test
	public void testAjouteInterventionSurUEInconnue() {
		Intervention inter = new Intervention(new Date(), 2, false, 9);
		inter.setUe(java);
		assertThrows(IllegalArgumentException.class, () -> {
			untel.ajouteIntervention(inter);
		}, "Une exception doit être levée si l'intervention est liée à une UE inconnue");
	}


	@Test
	public void testHeuresPrevuesPourUEInexistante() {
		assertEquals(0, untel.heuresPrevuesPourUE(java),
				"Les heures prévues pour une UE non ajoutée doivent être nulles");
	}

	@Test
	public void testResteAPlanifierSansServicePrevu() {
		// Tester resteAPlanifier quand aucun service n'est prévu pour l'UE
		assertEquals(0, untel.resteAPlanifier(java, TypeIntervention.CM),
				"Doit retourner 0 pour une UE sans service prévu");
	}

	@Test
	public void testAjouteInterventionSurUEAvecService() throws Exception {
		// Ajouter un service pour l'UE
		untel.ajouteEnseignement(java, 10, 0, 0);

		// Créer et ajouter une intervention
		Date date = new Date();
		Intervention intervention = new Intervention(date, 2, false, 10);
		intervention.setUe(java);

		// Vérifier que l'ajout d'intervention ne lève pas d'exception
		assertDoesNotThrow(() -> {
			untel.ajouteIntervention(intervention);
		}, "L'ajout d'intervention sur une UE avec service devrait réussir");
	}

	@Test
	public void testAjouteEnseignementExistant() {
		untel.ajouteEnseignement(uml, 5, 10, 3);

		untel.ajouteEnseignement(uml, 2, 5, 1);

		assertEquals(29, untel.heuresPrevuesPourUE(uml),
				"Les volumes d'heures doivent être cumulés pour une même UE");
	}

	@Test
	public void testAjouterPlusieursUE() {
		untel.ajouteEnseignement(uml, 5, 10, 3);
		untel.ajouteEnseignement(java, 2, 5, 1);
		assertEquals(2, untel.getLesServicesPrevus().size()); // Vérifie que deux services prévus ont été créés
		assertEquals(2, untel.getLesEnseignements().size()); // Vérifie que deux enseignements ont été ajoutés
	}


	@Test
	public void testResteAPlanifierAvecInterventions() throws Exception {
		// Ajouter un service prévu
		untel.ajouteEnseignement(java, 10, 0, 0);

		// Ajouter une intervention
		Date date = new Date();
		Intervention intervention = new Intervention(new Salle("Salle 101", 30), java, untel, TypeIntervention.CM, date, 5);
		untel.ajouteIntervention(intervention);

		// Vérifier le reste à planifier
		assertEquals(5, untel.resteAPlanifier(java, TypeIntervention.CM),
				"Le reste à planifier doit correspondre à la différence entre le service prévu et les interventions");
	}


	@Test
	public void testEnSousServiceLimite() {
		// Cas limite exact de 192 heures
		untel.ajouteEnseignement(uml, 0, 192, 0);
		assertFalse(untel.enSousService(),
				"Un enseignant avec exactement 192 heures n'est pas en sous-service");
	}













}
