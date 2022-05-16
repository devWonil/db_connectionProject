package project;

import java.util.ArrayList;

public interface IUpdate {

	ArrayList<MovieInfoDto> updateData(int movieNumber, String movieName, String releasedDate, long revenue, long audience, int screen, float starRating,  String updateMovieName);
	ArrayList<MovieInfoDto> updateData2(int actorNumber, String actorName, String birthYear, float height, float weight, String spouse);
	
}
