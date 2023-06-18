package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

@WebServlet("/servleti/author/*")
public class AuthorPage extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nick = req.getPathInfo().substring(1);
		
		String[] nickSplit = nick.split("/");
		if(nickSplit.length == 2) {
			req.setAttribute("authorNick", nickSplit[0]);
			String akcija = nickSplit[1];
			if(akcija.equals("new")) {
				req.getRequestDispatcher("/servleti/newEntry").forward(req, resp);
				return;
			}
			if(akcija.equals("edit")) {
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("entryId")));
				req.setAttribute("entry", entry);
				req.getRequestDispatcher("/servleti/newEntry").forward(req, resp);
				return;
			}
			req.setAttribute("entryId", akcija);
			req.getRequestDispatcher("/servleti/viewEntry").forward(req, resp);
			return;
		}
		
		req.setAttribute("authorNick", nick);
		req.setAttribute("entries", DAOProvider.getDAO().getBlogEntriesForUser(nick));
		req.getRequestDispatcher("/WEB-INF/pages/Autor.jsp").forward(req, resp);
	}
}
