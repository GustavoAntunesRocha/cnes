package br.com.gustavo.cnesCarregamentoDados;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

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
	    return Arrays.stream(files)
	      .map(FTPFile::getName)
	      .collect(Collectors.toList());
	}
	
	public static void downloadFile(FTPClient ftp, String source, String destination) throws IOException {
		File file = new File(destination);
	    FileOutputStream out = new FileOutputStream(file);
	    ftp.retrieveFile(source, out);
	}
	
	public static String atualizarCnes(String url, String diretorio) {
		try {
			FTPClient ftp = open(url, 21, "anonymous", "");
			Collection<String> files = listFiles(ftp, "/cnes");
			String arquivo = "BASE_DE_DADOS_CNES";
			String ultimaVersao = "0";
			for(String file : files) {
				if(file.startsWith(arquivo) && Integer.parseInt(file.split("_")[4].substring(0, 6)) > Integer.parseInt(ultimaVersao)) {
					ultimaVersao = file.split("_")[4];
					ultimaVersao = ultimaVersao.substring(0, 6);
				}
			}
			System.out.println("Última versão disponível: " + ultimaVersao);
			File fileAux = new File(diretorio);
			String[] fileList = fileAux.list();
			boolean possuiuArquivo = false;
			for(String fileName : fileList) {
				if(fileName.startsWith(arquivo)) {
					possuiuArquivo = true;
				}
			}
			for(String fileName : fileList) {
				if(fileName.startsWith(arquivo) && Integer.parseInt(fileName.split("_")[4].substring(0, 6)) < Integer.parseInt(ultimaVersao) || !possuiuArquivo) {
					System.out.println("A base de dados " + fileName + " será atualizada, aguarde...");
					arquivo = arquivo + "_" + ultimaVersao + ".ZIP";
					downloadFile(ftp, "/cnes/" + arquivo, diretorio + arquivo);
					ZipProcess.unZip(diretorio + arquivo, diretorio + "arquivos");
					close(ftp);
					return ultimaVersao;
				}
			}
			System.out.println("A base de dados já está na mais recente");
			close(ftp);
			return ultimaVersao;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
