package hr.javafx.realestate.javafxmanagementsystem.FileRepository;

import hr.javafx.realestate.javafxmanagementsystem.exception.EmptyRepositoryResultException;
import hr.javafx.realestate.javafxmanagementsystem.exception.FailedToAuthenticateException;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class LoginRepository {

    private static final String USER_FILE_PATH = "dat/user.txt";
    private static final Integer NUMBER_OF_ROWS_PER_USER = 2;

    public static String[] checkLogIn(String username, String password) throws FailedToAuthenticateException {

        String sha256hex = DigestUtils.sha256Hex(password);

        try(Stream<String> stream = Files.lines(Path.of(USER_FILE_PATH))){
            List<String> fileRows = stream.toList();

            for(Integer recordNumber = 0;
                recordNumber < (fileRows.size()/NUMBER_OF_ROWS_PER_USER); recordNumber++){
                String userName = fileRows.get(NUMBER_OF_ROWS_PER_USER * recordNumber + 0);
                String[] splitUsername = userName.split(";");
                String hashedPassword = fileRows.get(NUMBER_OF_ROWS_PER_USER*recordNumber + 1);
                if(splitUsername[0].equals(username)){
                    if(hashedPassword.equals(sha256hex)){
                        return splitUsername;
                    }
                    else {
                        throw new FailedToAuthenticateException();
                    }
                }
            }
        } catch(IOException e){
            throw new EmptyRepositoryResultException();
        }
        throw new FailedToAuthenticateException();
    }
}
