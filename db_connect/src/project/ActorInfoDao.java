package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActorInfoDao implements ISelect, ICreate, IDelete, IUpdate{

	private static final String TABLE_NAME = "배우정보";
	private DBClient client;
	private Connection connection;
	private ResultSet rs = null;
	
	private ArrayList<ActorInfoDto> actorInfo;
	
	public ActorInfoDao() {
		client = DBClient.getInstance();
		connection = client.getConnection();
	}
	
	public ArrayList<ActorInfoDto> SelectAll(){
		actorInfo = new ArrayList<ActorInfoDto>();
		
		 ArrayList<ActorInfoDto> dtoAll = new ArrayList<ActorInfoDto>();
		 
		try {
			String query = "select * from actorTbl";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			
			
			
			while(rs.next()) {
				
				ActorInfoDto dto = new ActorInfoDto();
				
				
				dto.setActoreNumber(rs.getInt("actorNumber"));
				dto.setActorName(rs.getString("actorName"));
				dto.setBirthYear(rs.getInt("birthYear"));
				dto.setHeight(rs.getFloat("height"));
				dto.setWeight(rs.getFloat("weight"));
				dto.setSpouse(rs.getString("spouse"));
				
				dtoAll.add(dto);
				
				
//				movieInfo.add(dto);
				
//				info.getModel().addRow(new Object[] {rs.getInt("movieNumber"),rs.getString("movieName"),
//						rs.getString("releasedDate"), rs.getLong("revenue"), rs.getLong("audience"), 
//						rs.getInt("screen"), rs.getFloat("starRating")});
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dtoAll;
	}
	@Override
	public void deleteData2(String actorName) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<ActorInfoDto> insertData(ActorInfoDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<ActorInfoDto> actorInfo(String actorName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<ActorInfoDto> selectByActorName(String actorName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ActorInfoDto> selectByBirthYear(String birthYear) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<MovieInfoDto> updateData2(int actorNumber, String actorName, String birthYear, float height,
			float weight, String spouse) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<MovieInfoDto> updateData(int movieNumber, String movieName, String releasedDate, long revenue,
			long audience, int screen, float starRating, String updateMovieName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteData(String movieName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<MovieInfoDto> insertData(MovieInfoDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MovieInfoDto> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public ArrayList<MovieInfoDto> selectByMovieName(String movieName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MovieInfoDto> selectByReleasedYear(String releasedYear) {
		// TODO Auto-generated method stub
		return null;
	}

}
