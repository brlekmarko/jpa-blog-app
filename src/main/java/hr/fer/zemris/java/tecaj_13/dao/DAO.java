package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Dohvaća korisnika sa zadanim <code>nick</code>-om. Ako takav ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param nick korisnikov nick
	 * @return ser ili <code>null</code> ako korisnik ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	
	/**
	 * Sprema predanog korisnika u bazu, vraća spremljenog korisnika s postavljenim id-em.
	 * 
	 * @return spremljeni korisnik
	 * @throws DAOException ako dođe do pogreške pri spremanju podataka
	 */
	public BlogUser saveBlogUser(BlogUser blogUser) throws DAOException;

	
	/**
	 * Dohvaća sve korisnike iz baze
	 * 
	 * @return lista spremljenih korisnika
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public List<BlogUser> getAllAuthors() throws DAOException;

	/**
	 * Dohvaća sve entrije od kojih kreator ima zadani nick.
	 * 
	 * @param nick korisnikov nick
	 * @return lista entrija od korisnika korisnika
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public List<BlogEntry> getBlogEntriesForUser(String nick) throws DAOException;

	/**
	 * Stvara novi blog entry s danim parametrima
	 * 
	 * @param title naslov entrya
	 * @param text tekst entrya
	 * @param userNick korisnikov nick
	 * @return spremljeni blogentry
	 * @throws DAOException ako dođe do pogreške pri dohvatu ili spremanju podataka
	 */
	public BlogEntry createNewBlogEntry(String title, String text, String userNick) throws DAOException;

	
	/**
	 * Updatea blog s danim id-em na dane parametre
	 * 
	 * @param title naslov entrya
	 * @param text tekst entrya
	 * @param entryId id entrya koji updateamo
	 * @return spremljeni blogentry
	 * @throws DAOException ako dođe do pogreške pri dohvatu ili spremanju podataka
	 */
	public BlogEntry updateBlogEntry(String title, String text, Long entryId) throws DAOException;
	
	
	/**
	 * Vraca sve komentare od entrya s danim id-jem
	 * 
	 * @param entryId id entrya od kojeg dohvacamo komentare
	 * @return komentari od zeljenog entrya
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public List<BlogComment> getCommentsForEntry(Long entryId) throws DAOException;

	
	/**
	 * Dodaje komentar na entry.
	 * 
	 * @param entryId id entrya na koji stavljamo komentar
	 * @param message poruka komentara
	 * @param usersEmail email korisnika koji stavlja komentar
	 * @return spremljeni komentar
	 * @throws DAOException ako dođe do pogreške pri dohvatu ili spremanju podataka
	 */
	public BlogComment addComment(Long entryId, String message, String usersEmail);
	
}