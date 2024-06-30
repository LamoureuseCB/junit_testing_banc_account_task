public class BankAccount {
    private boolean isBlocked = false;
    private Integer amount;
    private String currency;

    private final String firstName;
    private final String secondName;

    public BankAccount(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public void block() {
        this.isBlocked = true;
    }

    public void activate(String currency) {
        this.amount = 0;
        this.currency = currency;
    }

    public Integer getAmount() {
        if (amount == null) {
            throw new IllegalStateException("Счет не активирован");
        }
        return this.amount;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public String getCurrency() {
        return currency;
    }

    public String[] getFullName() {
        return new String[]{firstName, secondName};
    }

    public void deposit(int amount) {
        if (this.amount == null) {
            throw new IllegalStateException("Счет не активирован");
        }
        if (isBlocked) {
            throw new IllegalStateException("Счет заблокирован");
        }
        this.amount += amount;
    }

    public void withdraw(int amount) {
        if (isBlocked) {
            throw new IllegalStateException("Счет заблокирован");
        }
        if(this.amount < amount){
            throw new IllegalStateException("На счету недостаточно средств");
        }
        this.amount -= amount;
    }

    public void transfer(BankAccount otherAccount, int amount) {
        if (otherAccount == null) {
            throw new IllegalStateException("Счет получателя не существует");
        }
        withdraw(amount);
        otherAccount.deposit(amount);
    }
}
