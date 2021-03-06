package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Musicas {
	public static boolean cadastrado(int codigo) throws Exception {
		boolean retorno = false;

		try {
			String sql;

			sql = "SELECT * " + "FROM musicaS " + "WHERE CODIGO = ?";

			BDSQLServer.COMANDO.prepareStatement(sql);

			BDSQLServer.COMANDO.setInt(1, codigo);

			MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

			retorno = resultado.first(); // pode-se usar resultado.last() ou
											// resultado.next() ou
											// resultado.previous() ou
											// resultado.absotule(numeroDaLinha)

			/*
			 * // ou, se preferirmos,
			 * 
			 * String sql;
			 * 
			 * sql = "SELECT COUNT(*) AS QUANTOS " + "FROM musicaS " +
			 * "WHERE CODIGO = ?";
			 * 
			 * BDSQLServer.COMANDO.prepareStatement (sql);
			 * 
			 * BDSQLServer.COMANDO.setInt (1, codigo);
			 * 
			 * MeuResultSet resultado =
			 * (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
			 * 
			 * resultado.first();
			 * 
			 * retorno = resultado.getInt("QUANTOS") != 0;
			 * 
			 */
		} catch (SQLException erro) {
			throw new Exception("Erro ao procurar musica");
		}

		return retorno;
	}

	public static void incluir(Musica musica) throws Exception {
		if (musica == null)
			throw new Exception("musica nao fornecido");

		try {
			String sql;

			sql = "INSERT INTO musicaS " + "(CODIGO,NOME,PRECO) " + "VALUES " + "(?,?,?)";

			BDSQLServer.COMANDO.prepareStatement(sql);

			BDSQLServer.COMANDO.setInt(1, musica.getCodigo());
			BDSQLServer.COMANDO.setString(2, musica.getNome());
			BDSQLServer.COMANDO.setFloat(3, musica.getPreco());

			BDSQLServer.COMANDO.executeUpdate();
			BDSQLServer.COMANDO.commit();
		} catch (SQLException erro) {
			throw new Exception("Erro ao inserir musica");
		}
	}

	public static void excluir(int codigo) throws Exception {
		if (!cadastrado(codigo))
			throw new Exception("Nao cadastrado");

		try {
			String sql;

			sql = "DELETE FROM musicaS " + "WHERE CODIGO=?";

			BDSQLServer.COMANDO.prepareStatement(sql);

			BDSQLServer.COMANDO.setInt(1, codigo);

			BDSQLServer.COMANDO.executeUpdate();
			BDSQLServer.COMANDO.commit();
		} catch (SQLException erro) {
			throw new Exception("Erro ao excluir musica");
		}
	}

	public static void alterar(Musica musica) throws Exception {
		if (musica == null)
			throw new Exception("musica nao fornecido");

		if (!cadastrado(musica.getId()))
			throw new Exception("Nao cadastrado");

		try {
			String sql;

			sql = "UPDATE musicaS " + "SET NOME=? " + "SET PRECO=? " + "WHERE CODIGO = ?";

			BDSQLServer.COMANDO.prepareStatement(sql);

			BDSQLServer.COMANDO.setString(1, musica.getTitle());
			BDSQLServer.COMANDO.setFloat(2, musica.getPrice());
			BDSQLServer.COMANDO.setInt(3, musica.getId());

			BDSQLServer.COMANDO.executeUpdate();
			BDSQLServer.COMANDO.commit();
		} catch (SQLException erro) {
			throw new Exception("Erro ao atualizar dados de musica");
		}
	}

	public static Musica getMusica(int codigo) throws Exception {
		Musica musica = null;

		try {
			String sql;

			sql = "SELECT * " + "FROM musicaS " + "WHERE CODIGO = ?";

			BDSQLServer.COMANDO.prepareStatement(sql);

			BDSQLServer.COMANDO.setInt(1, codigo);

			MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

			if (!resultado.first())
				throw new Exception("Nao cadastrado");

			musica = new Musica(resultado.getString("TITULO"), resultado.getString("CANTOR"),
					resultado.getString("ESTILO"), resultado.getFloat("PRECO"), resultado.getFloat("DURACAO"));
		} catch (SQLException erro) {
			throw new Exception("Erro ao procurar musica");
		}

		return musica;
	}

	public static MeuResultSet getmusicas() throws Exception {
		MeuResultSet resultado = null;

		try {
			String sql;

			sql = "SELECT * " + "FROM musicaS";

			BDSQLServer.COMANDO.prepareStatement(sql);

			resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();
		} catch (SQLException erro) {
			throw new Exception("Erro ao recuperar musicas");
		}

		return resultado;
	}

	public static ListaDeMusicas<Musica> getMusicasTitulo(String titulo) throws Exception {

		MeuResultSet resultado = null;
		Musica musica = null;
		ListaDeMusicas<Musica> listademusicas = new ListaDeMusicas<Musica>();

		try {
			String sql;

			sql = "SELECT * FROM musicaS WHERE TITULO LIKE %?%";

			BDSQLServer.COMANDO.prepareStatement(sql);

			BDSQLServer.COMANDO.setString(1, titulo);

			resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

			if (!resultado.first()) {
				throw new Exception();
			}

			while (resultado.next()) {
				musica = new Musica(resultado.getString("TITULO"), resultado.getString("CANTOR"),
						resultado.getString("ESTILO"), resultado.getFloat("PRECO"), resultado.getFloat("DURACAO"));
				listademusicas.insereItem(musica);
			}

		} catch (SQLException erro) {
			throw new Exception("Erro ao recuperar musicas");
		}

		BDSQLServer.COMANDO.close();

		return listademusicas;
	}

	public static ListaDeMusicas<Musica> getMusicasEstilo(String estilo) throws Exception {

		MeuResultSet resultado = null;
		Musica musica = null;
		ListaDeMusicas<Musica> listademusicas = new ListaDeMusicas<Musica>();

		try {
			String sql;

			sql = "SELECT * FROM musicaS WHERE ESTILO LIKE %?%";

			BDSQLServer.COMANDO.prepareStatement(sql);

			BDSQLServer.COMANDO.setString(1, estilo);

			resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

			if (!resultado.first()) {
				throw new Exception();
			}

			while (resultado.next()) {
				musica = new Musica(resultado.getString("TITULO"), resultado.getString("CANTOR"),
						resultado.getString("ESTILO"), resultado.getFloat("PRECO"), resultado.getFloat("DURACAO"));
				listademusicas.insereItem(musica);
			}

		} catch (SQLException erro) {
			throw new Exception("Erro ao recuperar musicas");
		}

		BDSQLServer.COMANDO.close();

		return listademusicas;
	}

	public static ListaDeMusicas<Musica> getMusicasCantor(String cantor) throws Exception {

		MeuResultSet resultado = null;
		Musica musica = null;
		ListaDeMusicas<Musica> listademusicas = new ListaDeMusicas<Musica>();

		try {
			String sql;

			sql = "SELECT * FROM musicaS WHERE CANTOR LIKE %?%";

			BDSQLServer.COMANDO.prepareStatement(sql);

			BDSQLServer.COMANDO.setString(1, cantor);

			resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

			if (!resultado.first()) {
				throw new Exception();
			}

			while (resultado.next()) {
				musica = new Musica(resultado.getString("TITULO"), resultado.getString("CANTOR"),
						resultado.getString("ESTILO"), resultado.getFloat("PRECO"), resultado.getFloat("DURACAO"));
				listademusicas.insereItem(musica);
			}

		} catch (SQLException erro) {
			throw new Exception("Erro ao recuperar musicas");
		}

		BDSQLServer.COMANDO.close();

		return listademusicas;
	}
}