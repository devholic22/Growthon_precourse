package starting.precourse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Image(String url, User owner) {
        this.url = url;
        this.owner = owner;
    }
}
