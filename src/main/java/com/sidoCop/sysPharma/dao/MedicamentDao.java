package com.sidoCop.sysPharma.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException; // Import pour gérer l'exception
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sidoCop.sysPharma.domaine.model.Categorie;
import com.sidoCop.sysPharma.domaine.model.Medicament;

public class MedicamentDao implements IMedicamentDao {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// RowMapper réutilisable pour mapper un ResultSet à un objet Medicament
	private static final class MedicamentRowMapper implements RowMapper<Medicament> {
		@Override
		public Medicament mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Medicament(
					resultSet.getInt("id"),
					resultSet.getString("designation"),
					resultSet.getDouble("prix"),
					resultSet.getString("description"),
					resultSet.getString("image"),
					new Categorie(resultSet.getString("categorie")));
		}
	}

	@Override
	public Medicament recuperationMedicament(int id) {
		System.out.println("DAO: récupération du médicament id=" + id);

		Object[] arguments = new Object[] { id };

		try {
			return jdbcTemplate.queryForObject(
					"SELECT id, designation, prix, description, image, categorie FROM Medicament WHERE id = ?", // Sélectionne
																																																			// designationCategorie
					arguments,
					new MedicamentRowMapper());
		} catch (EmptyResultDataAccessException e) {
			System.out.println("DAO: Aucun médicament trouvé pour l'ID " + id);
			return null;
		}
	}

	@Override
	public void creerMedicament(Medicament medicament) {
		System.out.println("DAO: création du médicament " + medicament.toString());

		Object[] arguments = new Object[] {
				medicament.getDesignation(),
				medicament.getPrix(),
				medicament.getDescription(),
				medicament.getImage(),
				medicament.getCategorie().getDesignation()
		};
		jdbcTemplate.update(
				"INSERT INTO Medicament (designation, prix, description, image, categorie) VALUES (?, ?, ?, ?, ?)", // Insère
																																																						// dans
																																																						// designationCategorie
				arguments);
	}

	@Override
	public Medicament modifierMedicament(Medicament medicament) {
		System.out.println("DAO: modification du médicament " + medicament.toString());

		Object[] arguments = new Object[] {
				medicament.getDesignation(),
				medicament.getPrix(),
				medicament.getDescription(),
				medicament.getImage(),
				medicament.getCategorie().getDesignation(), // Nouvelle valeur pour designationCategorie
				medicament.getId() // ID du médicament à mettre à jour
		};

		jdbcTemplate.update(
				"UPDATE Medicament SET designation = ?, prix = ?, description = ?, image = ?, categorie = ? WHERE id = ?", // Met
																																																										// à
																																																										// jour
																																																										// designationCategorie
				arguments);
		return medicament;
	}

	@Override
	public void supprimerMedicament(Medicament medicament) {
		System.out.println("DAO: suppression du médicament " + medicament.toString());
		Object[] arguments = new Object[] { medicament.getId() };
		jdbcTemplate.update("DELETE FROM Medicament WHERE id = ?", arguments);
	}

	@Override
	public List<Medicament> recuperationListeMedicament() {
		System.out.println("DAO: récupération de tous les médicaments");

		return jdbcTemplate.query(
				"SELECT id, designation, prix, description, image, categorie FROM Medicament", // Sélectionne
																																												// designationCategorie
				new MedicamentRowMapper());
	}

	public void initialisation() {
		System.out.println("DAO: creation spring");
	}

	public void destruction() {
		System.out.println("DAO: destruction spring");
	}
}