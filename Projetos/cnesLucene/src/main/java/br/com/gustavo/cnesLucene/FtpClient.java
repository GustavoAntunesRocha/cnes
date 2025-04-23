package br.com.gustavo.cnesLucene;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesLucene.service.Indexer;

@Service
public abstract class FtpClient {

	public static FTPClient open(String server, int port, String user, String password) throws IOException {
		FTPClient ftp = new FTPClient();

		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

		ftp.connect(server, port);
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new IOException("Exception in connecting to FTP Server");
		}

		ftp.enterLocalPassiveMode();
		ftp.login(user, password);
		return ftp;
	}

	public static void close(FTPClient ftp) throws IOException {
		ftp.disconnect();
	}

	public static Collection<String> listFiles(FTPClient ftp, String path) throws IOException {
		FTPFile[] files = ftp.listFiles(path);
		return Arrays.stream(files).map(FTPFile::getName).collect(Collectors.toList());
	}

	public static void downloadFile(FTPClient ftp, String source, String destination) throws IOException {
		// Criar diretório pai do arquivo, se não existir
		Path localFilePath = Paths.get(destination);
		Files.createDirectories(localFilePath.getParent());

		try (FileOutputStream fos = new FileOutputStream(destination)) {
			if (!ftp.retrieveFile(source, fos)) {
				throw new IOException("Falha ao baixar o arquivo: " + source);
			}
		}
	}

	public static String atualizarCnes(String url, String diretorio) {
		try {
			// Criar diretórios necessários
			Path arquivosPath = Paths.get(diretorio);
			Files.createDirectories(arquivosPath);

			FTPClient ftp = open(url, 21, "anonymous", "");
			Collection<String> files = listFiles(ftp, "/cnes");
			String arquivo = "BASE_DE_DADOS_CNES";
			String ultimaVersao = "0";
			for (String file : files) {
				if (file.startsWith(arquivo)
						&& Integer.parseInt(file.split("_")[4].substring(0, 6)) > Integer.parseInt(ultimaVersao)) {
					ultimaVersao = file.split("_")[4];
					ultimaVersao = ultimaVersao.substring(0, 6);
				}
			}
			System.out.println("\nÚltima versão disponível: " + ultimaVersao);

			// Checar se a ultima versão já está indexada e se sim, retorna
			if (Indexer.getIndexedVersion().equals(ultimaVersao)) {
				System.out.println("A base de dados já está na mais recente");
				close(ftp);
				return ultimaVersao;
			}

			System.out.println("\nA base de dados " + ultimaVersao + " será baixada e atualizada, aguarde...");
			arquivo = arquivo + "_" + ultimaVersao + ".ZIP";
			downloadFile(ftp, "/cnes/" + arquivo, diretorio + arquivo);
			ZipProcess.unZip(diretorio + arquivo, diretorio);
			close(ftp);
			return ultimaVersao;
		} catch (IOException e) {
			System.err.println("Erro ao conectar ao servidor FTP: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
