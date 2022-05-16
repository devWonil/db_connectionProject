package project;

import java.util.ArrayList;

public interface ICreate {

	ArrayList<MovieInfoDto> insertData(MovieInfoDto dto);
	ArrayList<ActorInfoDto> insertData(ActorInfoDto dto);
	
}