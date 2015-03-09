public final class AccountOld implements Comparable {
    public AccountOld(String aFirstName, String aLastName, int aAccountNumber, int aBalance, boolean aIsNewAccount,
            AccountType aAccountType) {
        // ..parameter validations elided
        fFirstName = aFirstName;
        fLastName = aLastName;
        fAccountNumber = aAccountNumber;
        fBalance = aBalance;
        fIsNewAccount = aIsNewAccount;
        fAccountType = aAccountType;
    }

    /**
     * @param aThat
     *            is a non-null AccountOld.
     *
     * @throws NullPointerException
     *             if aThat is null.
     * @throws ClassCastException
     *             if aThat is not an AccountOld object.
     */
    public int compareTo(Object aThat) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        // this optimization is usually worthwhile, and can
        // always be added
        if (this == aThat)
            return EQUAL;

        final AccountOld that = (AccountOld) aThat;

        // primitive numbers follow this form
        if (this.fAccountNumber < that.fAccountNumber)
            return BEFORE;
        if (this.fAccountNumber > that.fAccountNumber)
            return AFTER;

        // booleans follow this form
        if (!this.fIsNewAccount && that.fIsNewAccount)
            return BEFORE;
        if (this.fIsNewAccount && !that.fIsNewAccount)
            return AFTER;

        // Objects, including type-safe enums, follow this form.
        // Exception : Boolean implements Comparable in JDK 1.5, but not in 1.4
        // Note that null objects will throw an exception here.
        int comparison = this.fAccountType.compareTo(that.fAccountType);
        if (comparison != EQUAL)
            return comparison;

        comparison = this.fLastName.compareTo(that.fLastName);
        if (comparison != EQUAL)
            return comparison;

        comparison = this.fFirstName.compareTo(that.fFirstName);
        if (comparison != EQUAL)
            return comparison;

        if (this.fBalance < that.fBalance)
            return BEFORE;
        if (this.fBalance > that.fBalance)
            return AFTER;

        // all comparisons have yielded equality
        // verify that compareTo is consistent with equals (optional)
        assert this.equals(that) : "compareTo inconsistent with equals.";

        return EQUAL;
    }

    /**
     * Define equality of state.
     */
    public boolean equals(Object aThat) {
        if (this == aThat)
            return true;
        if (!(aThat instanceof Account))
            return false;

        AccountOld that = (AccountOld) aThat;
        return (this.fAccountNumber == that.fAccountNumber) && (this.fAccountType == that.fAccountType)
                && (this.fBalance == that.fBalance) && (this.fIsNewAccount == that.fIsNewAccount)
                && (this.fFirstName.equals(that.fFirstName)) && (this.fLastName.equals(that.fLastName));
    }

    /**
     * A class that overrides equals must also override hashCode.
     */
    public int hashCode() {
        int result = HashCodeUtil.SEED;
        result = HashCodeUtil.hash(result, fAccountNumber);
        result = HashCodeUtil.hash(result, fAccountType);
        result = HashCodeUtil.hash(result, fBalance);
        result = HashCodeUtil.hash(result, fIsNewAccount);
        result = HashCodeUtil.hash(result, fFirstName);
        result = HashCodeUtil.hash(result, fLastName);
        return result;
    }

    // PRIVATE

    private String fFirstName; // non-null
    private String fLastName; // non-null
    private int fAccountNumber;
    private int fBalance;
    private boolean fIsNewAccount;

    /**
     * Type of the account, expressed as a type-safe enumeration (non-null).
     */
    private AccountType fAccountType;

    /**
     * Exercise compareTo.
     */
    public static void main(String[] aArguments) {
        // Note the difference in behaviour in equals and compareTo, for nulls:
        String text = "blah";
        Integer number = new Integer(10);
        // x.equals(null) always returns false:
        System.out.println("false: " + text.equals(null));
        System.out.println("false: " + number.equals(null));
        // x.compareTo(null) always throws NullPointerException:
        // System.out.println( text.compareTo(null) );
        // System.out.println( number.compareTo(null) );

        AccountOld flaubert = new AccountOld("Gustave", "Flaubert", 1003, 0, true, AccountType.MARGIN);

        // all of these other versions of "flaubert" differ from the
        // original in only one field
        AccountOld flaubert2 = new AccountOld("Guy", "Flaubert", 1003, 0, true, AccountType.MARGIN);
        AccountOld flaubert3 = new AccountOld("Gustave", "de Maupassant", 1003, 0, true, AccountType.MARGIN);
        AccountOld flaubert4 = new AccountOld("Gustave", "Flaubert", 2004, 0, true, AccountType.MARGIN);
        AccountOld flaubert5 = new AccountOld("Gustave", "Flaubert", 1003, 1, true, AccountType.MARGIN);
        AccountOld flaubert6 = new AccountOld("Gustave", "Flaubert", 1003, 0, false, AccountType.MARGIN);
        AccountOld flaubert7 = new AccountOld("Gustave", "Flaubert", 1003, 0, true, AccountType.CASH);

        System.out.println("0: " + flaubert.compareTo(flaubert));
        System.out.println("first name +: " + flaubert2.compareTo(flaubert));
        // Note capital letters precede small letters
        System.out.println("last name +: " + flaubert3.compareTo(flaubert));
        System.out.println("acct number +: " + flaubert4.compareTo(flaubert));
        System.out.println("balance +: " + flaubert5.compareTo(flaubert));
        System.out.println("is new -: " + flaubert6.compareTo(flaubert));
        System.out.println("account type -: " + flaubert7.compareTo(flaubert));
    }
}