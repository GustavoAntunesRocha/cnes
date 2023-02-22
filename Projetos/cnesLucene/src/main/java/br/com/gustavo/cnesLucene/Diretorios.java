package br.com.gustavo.cnesLucene;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Diretorios {

	private static final String BASE_PATH = "./data/arquivos";
	private static final String IN_DIR_ESTADOS = BASE_PATH + "/tbEstado";
	private static final String IN_DIR_MUNICIPIOS = BASE_PATH + "/tbMunicipio";
	private static final String IN_DIR_ENDERECOS = BASE_PATH + "/rlEstabEndCompl";
	private static final String IN_DIR_ESTABELECIMENTOS = BASE_PATH + "/tbEstabelecimento";
	private static final String IN_DIR_CBO = BASE_PATH + "/tbAtividadeProfissional";
	private static final String IN_DIR_PROFISSIONAIS = BASE_PATH + "/tbDadosProfissionalSus";
	private static final String IN_DIR_RL_PROFISSIONAIS_ESTABELECIMENTO = BASE_PATH + "/rlEstabEquipeProf";
	private static final String IN_DIR_TB_CARGA_HORARIA_SUS = BASE_PATH + "/tbCargaHorariaSus";

	private static List<String> versoes;

	public static List<String> getVersoes() {
		return versoes;
	}

	public static void setVersoes(List<String> versoes) {
		Diretorios.versoes = versoes;
	}

	public static String getBasePath() {
		return BASE_PATH;
	}

	public static String getInDirEstados() {
		return IN_DIR_ESTADOS;
	}

	public static String getInDirMunicipios() {
		return IN_DIR_MUNICIPIOS;
	}

	public static String getInDirEnderecos() {
		return IN_DIR_ENDERECOS;
	}

	public static String getInDirEstabelecimentos() {
		return IN_DIR_ESTABELECIMENTOS;
	}

	public static String getInDirCbo() {
		return IN_DIR_CBO;
	}

	public static String getInDirProfissionais() {
		return IN_DIR_PROFISSIONAIS;
	}

	public static String getInDirRlProfissionaisEstabelecimento() {
		return IN_DIR_RL_PROFISSIONAIS_ESTABELECIMENTO;
	}

	public static String getInDirTbCargaHorariaSus() {
		return IN_DIR_TB_CARGA_HORARIA_SUS;
	}

}
