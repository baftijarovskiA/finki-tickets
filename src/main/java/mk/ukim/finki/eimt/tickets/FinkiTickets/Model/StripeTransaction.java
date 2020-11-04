package mk.ukim.finki.eimt.tickets.FinkiTickets.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class StripeTransaction {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String transactionId;
        private String transactionStatus;
        private String transactionChargeId;
        private String transactionBalanceId;

        public StripeTransaction(String transactionId, String transactionStatus, String transactionChargeId, String transactionBalanceId) {
            this.transactionId = transactionId;
            this.transactionStatus = transactionStatus;
            this.transactionChargeId = transactionChargeId;
            this.transactionBalanceId = transactionBalanceId;
        }

        public StripeTransaction(){}

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        public String getTransactionChargeId() {
            return transactionChargeId;
        }

        public void setTransactionChargeId(String transactionChargeId) {
            this.transactionChargeId = transactionChargeId;
        }

        public String getTransactionBalanceId() {
            return transactionBalanceId;
        }

        public void setTransactionBalanceId(String transactionBalanceId) {
            this.transactionBalanceId = transactionBalanceId;
        }
}
