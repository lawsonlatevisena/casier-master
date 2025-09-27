package tg.ceel.cj.casierapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private String id;
    private String name;
    private File fileStorage;


}
