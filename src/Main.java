import model.EWalletSystem;
import service.EWalletApplicationServiceImpl;

public class Main {

    public static void main(String[] args) {
        EWalletApplicationServiceImpl eWalletApplicationService = new EWalletApplicationServiceImpl();
        eWalletApplicationService.Start();


    }
}