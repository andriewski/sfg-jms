package guru.springframework.sfgjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {

    private static final long serialVersionUID = -3274527082516429189L;

    private UUID id;
    private String message;

    public static HelloWorldMessage getMessage(String message) {
        return HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message(message)
                .build();
    }
}
