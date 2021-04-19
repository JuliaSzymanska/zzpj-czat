package tech.pzjswpooks.zzpj.chat.api.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "chat_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"chat_id", "account_id"})
})
@NamedQueries({
        @NamedQuery(name = "ChatUsersEntity.findAll", query = "SELECT a FROM ChatUsersEntity a"),
        @NamedQuery(name = "ChatUsersEntity.findById", query = "SELECT a FROM ChatUsersEntity a WHERE a.id = :id")
})
public class ChatUsersEntity {
    @Id
    @SequenceGenerator(name = "chat_users_generator", sequenceName = "chat_users_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_users_generator")
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne
    private ChatsEntity chatId;

    @Column(name = "account_id", nullable = false)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne
    private AccountsEntity accountId;


//    private ChatsEntity chatsByChatId;
//
//
//    private AccountsEntity accountsByAccountId;


    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId.getId();
    }

    public Long getAccountId() {
        return accountId.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChatUsersEntity that = (ChatUsersEntity) o;

        return new EqualsBuilder().append(id, that.id).append(chatId, that.chatId).append(accountId, that.accountId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(chatId).append(accountId).toHashCode();
    }

//    @ManyToOne
//    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false)
//    public ChatsEntity getChatsByChatId() {
//        return chatsByChatId;
//    }
//
//    public void setChatsByChatId(ChatsEntity chatsByChatId) {
//        this.chatsByChatId = chatsByChatId;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
//    public AccountsEntity getAccountsByAccountId() {
//        return accountsByAccountId;
//    }
//
//    public void setAccountsByAccountId(AccountsEntity accountsByAccountId) {
//        this.accountsByAccountId = accountsByAccountId;
//    }
}
