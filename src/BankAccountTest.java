import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BankAccountTest {
    @Test
    void shouldNotBeBlockedWhenCreated() {
        BankAccount bankAccount = new BankAccount("a", "b");
        assertFalse(bankAccount.isBlocked());
    }

    @Test
    void shouldReturnZeroAmountAfterActivation() {
        BankAccount bankAccount = new BankAccount("a", "b");
        bankAccount.activate("KZT");
        assertEquals(0, bankAccount.getAmount());
        assertEquals("KZT", bankAccount.getCurrency());
    }

    @Test
    void shouldBeBlockedAfterBlockedIsCalled() {
        BankAccount bankAccount = new BankAccount("a", "b");
        bankAccount.block();
        assertTrue(bankAccount.isBlocked());
    }

    @Test
    void shouldReturnFirstNameThenSecond() {
        BankAccount bankAccount = new BankAccount("a", "b");
        String[] fromConstructNameAndSurname = {"a", "b"};
        String[] nameAndSurname = bankAccount.getFullName();
        assertArrayEquals(fromConstructNameAndSurname, nameAndSurname);
    }

    @Test
    void shouldReturnNullAmountWhenNotActive() {
        BankAccount bankAccount = new BankAccount("a", "b");
        assertNull(bankAccount.getCurrency());
        String message = "Счет не активирован";
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> bankAccount.getAmount());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void exceptionWhenDepositAccountIsActivated() {
        BankAccount bankAccount = new BankAccount("a", "b");
        bankAccount.activate("KZT");
        bankAccount.deposit(5000);
        int expectedAmount = 5000;
        int realAmount = bankAccount.getAmount();
        assertEquals(expectedAmount, realAmount);
    }

    @Test
    void exceptionWhenDepositAccountIsActivatedAndBlocked() {
        BankAccount bankAccount = new BankAccount("a", "b");
        bankAccount.activate("KZT");
        bankAccount.block();
        String message = "Счет заблокирован";
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> bankAccount.deposit(1000));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldWithdrawalBalanceDecreasedToAmount() {
        BankAccount bankAccount = new BankAccount("a", "b");
        bankAccount.activate("KZT");
        bankAccount.deposit(1500);
        String message = "На счету недостаточно средств";
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> bankAccount.withdraw(2000));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldTransferAmountFromOneAccountToAnother() {
        BankAccount bankAccount = new BankAccount("a", "b");
        BankAccount recipientAccount = new BankAccount("c", "d");
        bankAccount.activate("KZT");
        recipientAccount.activate("KZT");
        bankAccount.deposit(1000);

        int bankAccountAmountBeforeTransit = bankAccount.getAmount();
        int recipientAccountAmountBeforeTransit = recipientAccount.getAmount();
        int amountOfTransit = 500;
        bankAccount.transfer(recipientAccount, amountOfTransit);

        int expectedbankAccountAmountAfterTransit = bankAccountAmountBeforeTransit - amountOfTransit;
        int expectedrecipientAccountAmountAfterTransit = recipientAccountAmountBeforeTransit + amountOfTransit;
        assertEquals(expectedbankAccountAmountAfterTransit, bankAccount.getAmount());
        assertEquals(expectedrecipientAccountAmountAfterTransit, recipientAccount.getAmount());
    }
    @Test
    void shouldTransferAmountFromOneAccountToAnotherNullAccount(){
        BankAccount bankAccount = new BankAccount("a", "b");
        BankAccount recipientAccount = null;
        bankAccount.activate("KZT");
        bankAccount.deposit(1000);
        int amountOfTransit = 1000;
        String message = "Счет получателя не существует";
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> bankAccount.transfer(recipientAccount, amountOfTransit));
        assertEquals(message, exception.getMessage());
    }

}
