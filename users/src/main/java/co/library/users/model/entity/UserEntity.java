package co.library.users.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="_id")
    private Long userId;

    private String email;
    private String password;
    private String name;
    private String address;

    @Column(name="type")
    private Integer userType; // 0-> normal user, 1 -> book seller, 2 -> admin
    @Column(name="can_sell")
    private Integer canSellBooks;
    @Column(name="no_of_books_bought")
    private Long bookBought;
    @Column(name="no_of_books_sold")
    private Long bookSold;

    private Integer isLoggedId;
    private Long loggingAttempt;

    public void setUserType(Integer userType) {
        this.userType = userType == null ? 0 : userType;
    }

    public void setCanSellBooks(Integer canSellBooks) {
        this.canSellBooks = canSellBooks == null ? 0 : canSellBooks;
    }

    public void setBookBought(Long bookBought) {
        this.bookBought = bookBought == null ? 0 : bookBought;
    }

    public void setBookSold(Long bookSold) {
        this.bookSold = bookSold == null ? 0 : bookSold;
    }

    public void setIsLoggedId(Integer isLoggedId) {
        this.isLoggedId = isLoggedId == null ? 0 : isLoggedId;
    }

    public void setLoggingAttempt(Long loggingAttempt) {
        this.loggingAttempt = loggingAttempt == null ? 0 : loggingAttempt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
