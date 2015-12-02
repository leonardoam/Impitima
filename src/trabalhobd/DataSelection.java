/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhobd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author leonardo
 */
public class DataSelection {
    private Connection conn;
    private PreparedStatement selectAllZonaStatement;
    private PreparedStatement selectAllSecaoStatement;
    private PreparedStatement selectAllUrnaStatement;
    private PreparedStatement selectAllPartidoStatement;
    private PreparedStatement selectAllPessoaStatement;
    private PreparedStatement selectAllFiliaStatement;
    private PreparedStatement selectAllFuncionarioStatement;
    private PreparedStatement selectAllPessoaTitNomeStatement;
    private PreparedStatement selectAllCandidatoPorCargoStatement;
    private PreparedStatement selectAllCandidatoStatement;
    
    private PreparedStatement selectCandidatoStatement;
    private PreparedStatement selectPartidoStatement;
    private PreparedStatement selectViceStatement;
    
    private PreparedStatement selectVotoPorCidadeCandidato;
    private PreparedStatement selectVotoPorCidadePartido;
    
    private CallableStatement executeCandidatosEleitos;
    private PreparedStatement selectAllCandidatosEleitos;
    
    private PreparedStatement rollupCandidato;
    private PreparedStatement rollupPartido;
    
    private PreparedStatement partidoInclusivo;
    private PreparedStatement maisVotados;
    
    public DataSelection(DBConnector conn) throws SQLException{
        this.conn = conn.getConnection();
        
        selectAllZonaStatement = this.conn.prepareStatement("SELECT * FROM zona WHERE 1=1 ORDER BY nroZona, estadoZona");
        selectAllSecaoStatement = this.conn.prepareStatement("SELECT * FROM secao WHERE 1=1 ORDER BY nroZona, estadoZona, nroSecao");
        selectAllUrnaStatement = this.conn.prepareStatement("SELECT * FROM urna WHERE 1=1 ORDER BY nroZona, estadoZona, nroSecao, nroUrna");
        selectAllPartidoStatement = this.conn.prepareStatement("SELECT * FROM partido WHERE 1=1 ORDER BY nroPartido");
        selectAllPessoaStatement = this.conn.prepareStatement("SELECT nroTitEleitor, UPPER(nomePessoa), UPPER(endPessoa), TO_CHAR(dataNasc, 'DD/MM/YYYY'), UPPER(escolaridade), UPPER(tipoPessoa), nroZona, estadoZona, nroSecao FROM pessoa WHERE 1=1 ORDER BY nomePessoa");
        selectAllPessoaTitNomeStatement = this.conn.prepareStatement("SELECT nrotiteleitor, nomepessoa FROM PESSOA ORDER BY nomepessoa");
        selectAllFiliaStatement = this.conn.prepareStatement("SELECT pessoa.nrotiteleitor, UPPER(pessoa.nomepessoa), partido.nropartido, partido.siglapartido, UPPER(partido.nomepartido)"
                                                            + " FROM FILIA, PESSOA, PARTIDO "
                                                            + " WHERE filia.nrotiteleitor = pessoa.nrotiteleitor"
                                                            + "  AND filia.nropartido = partido.nropartido"
                                                            + "  ORDER BY partido.nroPartido ASC");
        selectAllFuncionarioStatement = this.conn.prepareStatement("SELECT pessoa.nrotiteleitor, UPPER(pessoa.nomepessoa), UPPER(pessoa.endpessoa), TO_CHAR(dataNasc, 'DD/MM/YYYY'), UPPER(pessoa.escolaridade), UPPER(pessoa.tipopessoa), pessoa.nrozona, pessoa.estadozona, pessoa.nrosecao, UPPER(funcionario.cargofunc)"
                                                            + " FROM PESSOA"
                                                            + " LEFT JOIN FUNCIONARIO"
                                                            + " ON PESSOA.NROTITELEITOR = FUNCIONARIO.NROTITELEITOR"
                                                            + " ORDER BY pessoa.nomepessoa");
        selectAllCandidatoStatement = this.conn.prepareStatement("SELECT nrotiteleitor, UPPER(nomefantasia), nrocandidato, UPPER(cargocandidato), nrovotos" +
                                                                 "  FROM candidato" +
                                                                 "  ORDER BY cargocandidato, nomefantasia");
        selectAllCandidatoPorCargoStatement = this.conn.prepareStatement("SELECT candidato.nrotiteleitor, UPPER(pessoa.nomepessoa), UPPER(candidato.nomefantasia), UPPER(candidato.cargocandidato), candidato.nrocandidato" +
                                                                        "  FROM PESSOA, CANDIDATO" +
                                                                        "  WHERE PESSOA.NROTITELEITOR = CANDIDATO.NROTITELEITOR" +
                                                                        "    AND UPPER(CANDIDATO.CARGOCANDIDATO) LIKE UPPER(?)");
        selectCandidatoStatement = this.conn.prepareStatement("SELECT UPPER(candidato.nomefantasia), UPPER(candidato.cargocandidato), candidato.nrotiteleitor, partido.siglapartido" +
                                                                "    FROM CANDIDATO" +
                                                                "    LEFT JOIN FILIA" +
                                                                "    ON FILIA.NROTITELEITOR = CANDIDATO.NROTITELEITOR" +
                                                                "    JOIN PARTIDO" +
                                                                "    ON FILIA.NROPARTIDO = PARTIDO.NROPARTIDO" +
                                                                "    AND CANDIDATO.NROCANDIDATO = ?" +
                                                                "    AND UPPER(CANDIDATO.CARGOCANDIDATO) LIKE UPPER(?)");
        selectPartidoStatement = this.conn.prepareStatement("SELECT nropartido, UPPER(nomepartido), siglapartido FROM PARTIDO WHERE nropartido = ?");
        selectViceStatement = this.conn.prepareStatement("SELECT UPPER(candidato.nomefantasia), UPPER(candidato.cargocandidato), candidato.nrotiteleitor, PARTIDO.SIGLAPARTIDO" +
                                                                "    FROM CANDIDATO" +
                                                                "    JOIN EHVICEDE" +
                                                                "    ON EHVICEDE.NROTITELEITORVICE = CANDIDATO.NROTITELEITOR" +
                                                                "    AND EHVICEDE.NROTITELEITORPRINCIPAL = ?" +
                                                                "    LEFT JOIN FILIA" +
                                                                "    ON FILIA.NROTITELEITOR = CANDIDATO.NROTITELEITOR" +
                                                                "    JOIN PARTIDO" +
                                                                "    ON PARTIDO.NROPARTIDO = FILIA.NROPARTIDO");
        selectVotoPorCidadeCandidato = this.conn.prepareStatement("SELECT candidato.nomeFantasia, CANDIDATO.NROCANDIDATO, PARTIDO.SIGLAPARTIDO, COUNT(candidato.NROTITELEITOR)\n" +
                                                            "  FROM candidato, zona, secao, votocandidato, pessoa, partido, filia\n" +
                                                            "   WHERE\n" +
                                                            "     candidato.NROTITELEITOR = pessoa.nroTitEleitor AND\n" +
                                                            "     candidato.nrotiteleitor = filia.nrotiteleitor AND\n" +
                                                            "     partido.nropartido = filia.nropartido AND\n" +
                                                            "     votocandidato.nrotiteleitor = candidato.nrotiteleitor AND\n" +
                                                            "     votocandidato.nroZona = secao.nroZona AND\n" +
                                                            "     votocandidato.nroSecao = secao.nroSecao AND\n" +
                                                            "     votocandidato.estadoZona = secao.estadoZona AND\n" +
                                                            "     zona.nroZona = secao.nroZona AND\n" +
                                                            "     zona.estadoZona = secao.estadoZona AND\n" +
                                                            "     (UPPER(secao.LOCALSECAO) LIKE UPPER(?) OR\n" +
                                                            "     UPPER(zona.ENDZONA) LIKE UPPER(?)) AND\n" +
                                                            "     UPPER(CANDIDATO.CARGOCANDIDATO) LIKE UPPER(?)\n" +
                                                            "    GROUP BY(candidato.nomeFantasia, CANDIDATO.NROCANDIDATO, PARTIDO.SIGLAPARTIDO)\n" +
                                                            "    ORDER BY(COUNT(candidato.NROTITELEITOR)) DESC");
        
        selectVotoPorCidadePartido = this.conn.prepareStatement("SELECT PARTIDO.NOMEPARTIDO, PARTIDO.SIGLAPARTIDO, PARTIDO.NROPARTIDO, COUNT(PARTIDO.NROPARTIDO)\n" +
                                                            "  FROM zona, secao, votopartido, partido\n" +
                                                            "   WHERE\n" +
                                                            "     votopartido.nropartido = partido.nropartido AND\n" +
                                                            "     votopartido.nroZona = secao.nroZona AND\n" +
                                                            "     votopartido.nroSecao = secao.nroSecao AND\n" +
                                                            "     votopartido.estadoZona = secao.estadoZona AND\n" +
                                                            "     zona.nrozona = secao.nrozona AND\n" +
                                                            "     zona.estadozona = secao.estadozona AND\n" +
                                                            "     (UPPER(secao.LOCALSECAO) LIKE UPPER(?) OR\n" +
                                                            "     UPPER(zona.ENDZONA) LIKE UPPER(?))\n" +
                                                            "    GROUP BY(PARTIDO.NOMEPARTIDO, PARTIDO.SIGLAPARTIDO, PARTIDO.NROPARTIDO)\n" +
                                                            "    ORDER BY(COUNT(PARTIDO.NROPARTIDO)) DESC");     
        executeCandidatosEleitos = this.conn.prepareCall("{ call eleicoes.atualiza_eleitos(?, ?) }");
        selectAllCandidatosEleitos = this.conn.prepareStatement("SELECT * FROM candidatos_eleitos ORDER BY DECODE(CARGOCANDIDATO, 'PREFEITO', 1, 'VICE-PREFEITO', 2, 'VEREADOR', 3), NROVOTOS DESC");
        rollupCandidato = this.conn.prepareStatement("SELECT * from TABLE(eleicoes.relatorio_votos_candidatos)");
        rollupPartido = this.conn.prepareStatement("SELECT * from TABLE(eleicoes.relatorio_votos_partidos(?))");
        maisVotados = this.conn.prepareStatement("SELECT CANDIDATO.NOMEFANTASIA AS \"CANDIDATO\", CANDIDATO.CARGOCANDIDATO AS \"CARGO\", PARTIDO.SIGLAPARTIDO AS \"PARTIDO\", COUNT(VOTOCANDIDATO.NROTITELEITOR) \"NUMERO DE VOTOS\"\n" +
                                                "        FROM CANDIDATO\n" +
                                                "        LEFT JOIN VOTOCANDIDATO\n" +
                                                "               ON CANDIDATO.NROTITELEITOR = VOTOCANDIDATO.NROTITELEITOR\n" +
                                                "        LEFT JOIN FILIA\n" +
                                                "               ON CANDIDATO.NROTITELEITOR = FILIA.NROTITELEITOR\n" +
                                                "        LEFT JOIN PARTIDO\n" +
                                                "               ON FILIA.NROPARTIDO = PARTIDO.NROPARTIDO\n" +
                                                "        GROUP BY CANDIDATO.NOMEFANTASIA, CANDIDATO.CARGOCANDIDATO, PARTIDO.SIGLAPARTIDO\n" +
                                                "        HAVING COUNT(VOTOCANDIDATO.NROTITELEITOR) > ?\n" +
                                                "        ORDER BY \"NUMERO DE VOTOS\" DESC");
        partidoInclusivo = this.conn.prepareStatement("SELECT siglaPartido, nomePartido\n" +
                                                    "    FROM partido\n" +
                                                    "      WHERE NOT EXISTS(\n" +
                                                    "       (\n" +
                                                    "        SELECT DISTINCT escolaridade\n" +
                                                    "          FROM pessoa\n" +
                                                    "       )\n" +
                                                    "        MINUS\n" +
                                                    "       (\n" +
                                                    "        SELECT escolaridade\n" +
                                                    "         FROM candidato, pessoa, filia\n" +
                                                    "          WHERE(\n" +
                                                    "            candidato.NROTITELEITOR = pessoa.NROTITELEITOR AND\n" +
                                                    "            candidato.NROTITELEITOR = filia.NROTITELEITOR AND\n" +
                                                    "            filia.NROPARTIDO = partido.NROPARTIDO\n" +
                                                    "          )\n" +
                                                    "          GROUP BY (nroPartido, escolaridade)\n" +
                                                    "        )\n" +
                                                    "      )");
    }
    
    public ResultSet selectAllZonas() throws SQLException{
        return selectAllZonaStatement.executeQuery();
    }
    
    public ResultSet selectAllSecao() throws SQLException{
        return selectAllSecaoStatement.executeQuery();
    }
    
    public ResultSet selectAllUrna() throws SQLException{
        return selectAllUrnaStatement.executeQuery();
    }
    
    public ResultSet selectAllPartido() throws SQLException{
        return selectAllPartidoStatement.executeQuery();
    }
    
    public ResultSet selectAllPessoa() throws SQLException{
        return selectAllPessoaStatement.executeQuery();
    }
    public ResultSet selectAllTitNomePessoa() throws SQLException{
        return selectAllPessoaTitNomeStatement.executeQuery();
    }
    
    public ResultSet selectAllFilia() throws SQLException{
        return selectAllFiliaStatement.executeQuery();
    }
    
    public ResultSet selectAllFuncionario() throws SQLException{
        return selectAllFuncionarioStatement.executeQuery();
    }
    
    public ResultSet selectAllCandidato() throws SQLException{
        return selectAllCandidatoStatement.executeQuery();
    }
    
    public ResultSet selectAllCandidatoPorCargo(String cargo) throws SQLException{
        selectAllCandidatoPorCargoStatement.setString(1, cargo);
        return selectAllCandidatoPorCargoStatement.executeQuery();
    }
    
    public ResultSet selectCandidato(String numTitEleitor, String cargo) throws SQLException{
        selectCandidatoStatement.setString(1, numTitEleitor);
        selectCandidatoStatement.setString(2, cargo);
        return selectCandidatoStatement.executeQuery();
    }
    public ResultSet selectPartido(String nroPartido) throws SQLException{
        selectPartidoStatement.setInt(1, Integer.parseInt(nroPartido));
        return selectPartidoStatement.executeQuery();
    }
    public ResultSet selectVice(String nroTitEleitor) throws SQLException{
        selectViceStatement.setString(1, nroTitEleitor);
        return selectViceStatement.executeQuery();
    }
    public ResultSet selectVotoCandidatoPorCidade(String cidade, String cargo) throws SQLException{
        selectVotoPorCidadeCandidato.setString(1, "%"+cidade);
        selectVotoPorCidadeCandidato.setString(2, "%"+cidade);
        selectVotoPorCidadeCandidato.setString(3, cargo);
        return selectVotoPorCidadeCandidato.executeQuery();
    }
    public ResultSet selectVotoPartidoPorCidade(String cidade) throws SQLException{
        selectVotoPorCidadePartido.setString(1, "%"+cidade);
        selectVotoPorCidadePartido.setString(2, "%"+cidade);
        return selectVotoPorCidadePartido.executeQuery();
    }
    
    public ResultSet selectAllEleitos(int nver, String cidade) throws SQLException{
        executeCandidatosEleitos.setInt(1, nver);
        executeCandidatosEleitos.setString(2, cidade);
        executeCandidatosEleitos.executeQuery();
        conn.commit();
        return selectAllCandidatosEleitos.executeQuery();
    }
    
    public ResultSet selectResumoCandidato() throws SQLException{
        return rollupCandidato.executeQuery();
    }
    
    public ResultSet selectResumoPartido(int nvotos) throws SQLException{
        rollupPartido.setInt(1, nvotos);
        return rollupPartido.executeQuery();
    }
    
    public ResultSet selectMaisVotados(int nvotos) throws SQLException{
        maisVotados.setInt(1, nvotos);
        return maisVotados.executeQuery();
    }
    
    public ResultSet selectPartidoInclusivo() throws SQLException{
        return partidoInclusivo.executeQuery();
    }
}