package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;


@WebServlet("/servleti/main")
public class MainPage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("autori", DAOProvider.getDAO().getAllAuthors());
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// korisnik je trebao poslati nick i password s kojim se logira
		String nick = req.getParameter("nick");
		String password = req.getParameter("pass");
		
		// setamo atribute da ih mozemo staviti kao pocetne vrijednosti na ponovnom ucitavanju
		req.setAttribute("nick", nick);

		if (nick == null || password == null) {
			req.setAttribute("message", "Nick and password must be provided.");
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}
		
		// u bazi je spremljen hashirani password, potrebo hashirati njegov unos da usporedimo
		String hashedPassword = null;
		try{
			hashedPassword = new String(MessageDigest.getInstance("SHA-1").digest(password.getBytes("UTF-8")), "UTF-8");
		} catch (Exception e) {
			req.setAttribute("message", "Error while hashing password.");
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}

		// dohvacamo korisika s tim nickom iz baze da vidimo dal postoji i da usporedimo sifre
		BlogUser user = null;
		try {
			user = DAOProvider.getDAO().getBlogUser(nick);
		} catch (Exception e) {
			req.setAttribute("message", "Invalid nick or password.");
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}

		if (user == null || !user.getPasswordHash().equals(hashedPassword)) {
			req.setAttribute("message", "Invalid nick or password.");
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}

		//postavimo podatke u session
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		req.getSession().setAttribute("current.user.email", user.getEmail());

		resp.sendRedirect("main");
		
	}
}
