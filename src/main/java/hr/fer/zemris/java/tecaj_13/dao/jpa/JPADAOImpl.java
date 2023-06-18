package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		List<BlogUser> rezultat = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.getUser").setParameter("nick", nick).getResultList();
		if(rezultat.size() == 0) return null;
		return rezultat.get(0);
	}
	
	@Override
	public BlogUser saveBlogUser(BlogUser blogUser) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(blogUser);
		return blogUser;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getAllAuthors() throws DAOException {
		return JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.getAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntriesForUser(String nick) throws DAOException {
		return JPAEMProvider.getEntityManager().createNamedQuery("BlogEntry.entryOdKorisnika").setParameter("nick", nick).getResultList();
	}
	
	
	@Override
	public BlogEntry createNewBlogEntry(String title, String text, String userNick) throws DAOException {
		BlogUser user = getBlogUser(userNick);
		if(user == null) throw new DAOException("User doesn't exist");
		
		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle(title);
		blogEntry.setText(text);
		blogEntry.setCreator(user);
		
		JPAEMProvider.getEntityManager().persist(blogEntry);
		return blogEntry;
	}
	
	@Override
	public BlogEntry updateBlogEntry(String title, String text, Long entryId) throws DAOException {
		
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, entryId);
		if(blogEntry == null) throw new DAOException("Blog entry doesn't exist");

		blogEntry.setLastModifiedAt(new Date());
		blogEntry.setTitle(title);
		blogEntry.setText(text);

		
		JPAEMProvider.getEntityManager().merge(blogEntry);
		
		return blogEntry;
	}
	
	@Override
	public List<BlogComment> getCommentsForEntry(Long entryId) throws DAOException {
		
		BlogEntry blogEntry = getBlogEntry(entryId);
		
		if (blogEntry == null) return new ArrayList<>();

		
		@SuppressWarnings("unchecked")
		List<BlogComment> comments = 
				(List<BlogComment>)JPAEMProvider.getEntityManager().createQuery("select b from BlogComment as b where b.blogEntry=:be")
					.setParameter("be", blogEntry)
					.getResultList();
		
		return comments;
	}
	
	
	@Override
	public BlogComment addComment(Long entryId, String message, String usersEmail) {
		
		BlogEntry blogEntry = getBlogEntry(entryId);
		
		if (blogEntry == null) return null;
		
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail(usersEmail);
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(message);
		blogComment.setBlogEntry(blogEntry);
		
		em.persist(blogComment);

		blogEntry.getComments().add(blogComment);
		
		return blogComment;
		
	}

}