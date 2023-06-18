package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

@WebServlet("/servleti/addComment")
public class AddCommentServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long entryId = Long.parseLong((String)req.getParameter("entryId"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
		
		String message = req.getParameter("message");
		String usersEmail = (String) req.getSession().getAttribute("current.user.email");
		
		// dodamo komentar
		DAOProvider.getDAO().addComment(entryId, message, usersEmail);
		
		// vracamo ga nazad na taj entry
		resp.sendRedirect("/blog/servleti/author/" + entry.getCreator().getNick() + "/" + entry.getId());
	}
}
