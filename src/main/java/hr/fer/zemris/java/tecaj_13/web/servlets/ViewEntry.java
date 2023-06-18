package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

@WebServlet("/servleti/viewEntry")
public class ViewEntry extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long entryId = Long.parseLong((String)req.getAttribute("entryId"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
		req.setAttribute("entry", entry);
		
		List<BlogComment> comments = DAOProvider.getDAO().getCommentsForEntry(entryId);
		req.setAttribute("comments", comments);
		
		req.getRequestDispatcher("/WEB-INF/pages/ViewEntry.jsp").forward(req, resp);
	}
}
