package com.gbyzzz.linkedinjobsbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.gbyzzz.linkedinjobsbot.service.impl.Workplace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("linkedin_jobs")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {
    @Id
    @JsonProperty("jobPostingId")
    private Long id;
    @JsonProperty("title")
    private String name;

    private String description;
    @JsonProperty("formattedEmploymentStatus")
    private String type;


    private String workplace;

    @JsonProperty("formattedExperienceLevel")
    private String level;

    @JsonProperty("applies")
    private int applies;

    private String companyName;

    @JsonProperty("formattedLocation")
    private String location;

    private Set<String> searchLocations;

    private boolean saved;

    private boolean applied;

    private Date listedAt;

    private Date modifiedAt;

    private Date expiredAt;

    @JsonProperty("description")
    private void setDescription(JsonNode descriptionNode) {
        this.description = descriptionNode.get("text").asText();
    }

    @JsonProperty("workplaceTypes")
    private void setWorkplace(JsonNode workplaceNode) {
        this.workplace = Workplace.getWorkplace(workplaceNode.get("text").asText());
    }

    @JsonProperty("companyDetails")
    private void setCompanyName(JsonNode companyDetailsNode) {

        if (companyDetailsNode
                .has("com.linkedin.voyager.deco.jobs.web.shared.WebJobPostingCompany")) {
            this.companyName = companyDetailsNode
                    .get("com.linkedin.voyager.deco.jobs.web.shared.WebJobPostingCompany")
                    .get("companyResolutionResult").get("name").asText();
        } else if(companyDetailsNode
                .has("com.linkedin.voyager.jobs.JobPostingCompanyName")){
            this.companyName = companyDetailsNode
                    .get("com.linkedin.voyager.jobs.JobPostingCompanyName")
                    .get("companyName").asText();
        }
    }

    @JsonProperty("savingInfo")
    private void setSaved(JsonNode savingInfoNode) {
        this.saved = savingInfoNode.get("saved").asBoolean();
        if(savingInfoNode.has("savedAt")) {
            this.modifiedAt = new Date(savingInfoNode.get("savedAt").asLong());
        }
    }

    @JsonProperty("applyingInfo")
    private void setApplied(JsonNode applyingInfoNode) {
        this.saved = applyingInfoNode.get("applied").asBoolean();
    }

    @JsonProperty("listedAt")
    private void setListedAt(Long listedAtMillis) {
        this.listedAt = new Date(listedAtMillis);
    }

    @JsonProperty("expireAt")
    private void setExpiredAt(Long expireAtMillis) {
        this.expiredAt = new Date(expireAtMillis);
    }
}
