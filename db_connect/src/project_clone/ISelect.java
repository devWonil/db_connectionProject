package project_clone;

import java.util.ArrayList;

public interface ISelect {

	ArrayList<MovieInfoDto> selectAll();
	
	ArrayList<MovieInfoDto> selectByMovieName(String movieName);
	
	ArrayList<MovieInfoDto> selectByReleasedYear(String releasedYear);
	
	
}
