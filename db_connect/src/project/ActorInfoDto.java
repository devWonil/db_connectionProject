package project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString 
public class ActorInfoDto {

	private int actoreNumber;
	private String actorName;
	private int birthYear;
	private float height;
	private float weight;
	private String spouse;
	
}
