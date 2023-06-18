package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;


@WebServlet("/servleti/newEntry")
public class NewEntryPage extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/NewEntry.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		String entryId = req.getParameter("entryId");
		String userNick = (String)req.getSession().getAttribute("current.user.nick");
		
		BlogEntry entry = null;
		if(entryId == null) {
			entry = DAOProvider.getDAO().createNewBlogEntry(title, text, userNick);
		}else {
			entry = DAOProvider.getDAO().updateBlogEntry(title, text, Long.parseLong(entryId));
		}
		
		resp.sendRedirect("/blog/servleti/author/" + userNick + "/" + entry.getId());
	}

}
