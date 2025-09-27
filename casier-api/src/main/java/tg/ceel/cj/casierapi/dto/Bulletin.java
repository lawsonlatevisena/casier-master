package tg.ceel.cj.casierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bulletin {
    private byte[] bulletin;
    private String relativePath;
}
