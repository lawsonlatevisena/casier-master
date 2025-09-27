package tg.ceel.cj.casierapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackModel {
    private String title;
    private String message;
    private String process;
    private String record;
    private String step;
    private Integer order;
    private String feedbackTaskId;
    private ModelData data;

}
