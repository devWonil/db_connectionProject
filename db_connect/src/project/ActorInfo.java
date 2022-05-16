package project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import lombok.Data;

@Data
public class ActorInfo extends JFrame implements ItemListener {

	ActorInfoDao dao = new ActorInfoDao();

	// 카테고리 검색
	private JPanel searchPanel;
	private TitledBorder categoryBorder;

	// crud
	private JPanel crudPanel;
//	private TitledBorder crudBorder;

	private JTabbedPane jTabbedPane;
	
//	private JButton createButton;
//	private JButton updateButton;
//	private JButton deleteButton;

	private JLabel optionLabel;
	
	private JRadioButton actorName;
	private JRadioButton birthYear;

	private JTextField actorNameField;
	private JTextField birthYearField;

	private JButton actorNameButton;
	private JButton birthYearButton;

	private JComboBox comboBox;

	private JButton confirmBotton;
	
	// 영화 조회
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JPanel mainPanel;

	// 영화 테이블
	String schema[] = { "배우번호", "이름", "출생년도", "키", "체중", "배우자"};

	public ActorInfo() {

		setBounds(100, 100, 628, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 메인 패널
		mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(null);

		// 카테고리로 검색
		searchPanel = new JPanel();
		searchPanel.setLayout(null);
		categoryBorder = new TitledBorder("카테고리 검색");
		searchPanel.setBorder(categoryBorder);
		searchPanel.setBounds(10, 80, 592, 105);
		mainPanel.add(searchPanel);

		//
		crudPanel = new JPanel();
		crudPanel.setLayout(null);

		optionLabel = new JLabel("옵션을 선택하세요");
		optionLabel.setBounds(180, 3, 200, 30);
		crudPanel.add(optionLabel);
		
		confirmBotton = new JButton("확인");
		confirmBotton.setBounds(410, 8, 70, 20);
		crudPanel.add(confirmBotton);
		
		
		jTabbedPane = new JTabbedPane();
		jTabbedPane.setBounds(10, 10, 592, 65);
		jTabbedPane.setTabPlacement(JTabbedPane.TOP);
		jTabbedPane.add("crud", crudPanel);
		
		
		mainPanel.add(jTabbedPane);
		
		
		model = new DefaultTableModel(null, schema);
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 200, 588,330);
		scrollPane.setViewportView(table);
		mainPanel.add(scrollPane);

		// 라디오 박스
		actorName = new JRadioButton("이름");
		actorName.setBounds(80, 27, 50, 12);
		searchPanel.add(actorName);

		birthYear = new JRadioButton("출생년도");
		birthYear.setBounds(80, 67, 70, 12);
		searchPanel.add(birthYear);

		actorNameField = new JTextField("최민식");
		actorNameField.setBounds(180, 20, 230, 26);
		searchPanel.add(actorNameField);

		birthYearField = new JTextField("1967");
		birthYearField.setBounds(180, 60, 230, 26);
		searchPanel.add(birthYearField);
		
		actorNameButton = new JButton("검색");
		actorNameButton.setBounds(440, 20, 65, 25);
		searchPanel.add(actorNameButton);
		
		birthYearButton = new JButton("검색");
		birthYearButton.setBounds(440, 60, 65, 25);
		searchPanel.add(birthYearButton);
		
//		searchButton.addActionListener(this);
//        releaseDate.addActionListener(this);

		actorName.addItemListener(this);
		birthYear.addItemListener(this);

		actorNameField.setEnabled(false);
		birthYear.setEnabled(false);
		actorNameButton.setEnabled(false);
		birthYear.setEnabled(false);		
		
		
		
		DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
		defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel columnModel = table.getColumnModel();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setCellRenderer(defaultTableCellRenderer);
		}

		SelectAll();

        comboBox = new JComboBox();
        comboBox.setBounds(300, 8, 70, 20);
        crudPanel.add(comboBox);
//        comboBox.addItem("선택");
        comboBox.addItem("생성");
        comboBox.addItem("수정");
        comboBox.addItem("삭제");

        confirmBotton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() == 0) {
					new CreatePanel();
				} else if(comboBox.getSelectedIndex() == 1) {
					new UpdatePanel();
				} else if(comboBox.getSelectedIndex() == 2) {
					new DeletePanel();
				}
			}
		});
        
        setLocationRelativeTo(null);
		setVisible(true);

	}

	public void SelectAll() {
		
		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i > -1; i--) {
				model.removeRow(i);
			}
		}
		
		for (int i = 0; i < dao.selectAll().size(); i++) {
			// 여기 확인
			model.addRow(new Object[] { dao.selectAll().get(i).getMovieNumber(), dao.selectAll().get(i).getMovieName(),
					dao.selectAll().get(i).getReleasedDate(), dao.selectAll().get(i).getRevenue(),
					dao.selectAll().get(i).getAudience(), dao.selectAll().get(i).getScreen(),
					dao.selectAll().get(i).getStarRating() });
		}
	}

	// 라디오 버튼
	@Override
	public void itemStateChanged(ItemEvent e1) {

		// 영화 제목 버튼 클릭됨
		if (e1.getSource() == actorName) {
			actorNameField.setEnabled(true);
			actorNameButton.setEnabled(true);
			birthYear.setEnabled(false);
			// 영화 쿼리 뿌림

			actorNameButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == actorNameButton) {
						dao.selectByActorName(actorNameField.getText());

						if (model.getRowCount() > 0) {
							for (int i = model.getRowCount() - 1; i > -1; i--) {
								model.removeRow(i);
							}
						}

						for (int i = 0; i < dao.selectByActorName(actorNameField.getText()).size(); i++) {
							System.out.println("영화제목");
							model.addRow(new Object[] {
									dao.selectByMovieName(actorNameField.getText()).get(i).getMovieNumber(),
									dao.selectByMovieName(actorNameField.getText()).get(i).getMovieName(),
									dao.selectByMovieName(actorNameField.getText()).get(i).getReleasedDate(),
									dao.selectByMovieName(actorNameField.getText()).get(i).getRevenue(),
									dao.selectByMovieName(actorNameField.getText()).get(i).getAudience(),
									dao.selectByMovieName(actorNameField.getText()).get(i).getScreen(),
									dao.selectByMovieName(actorNameField.getText()).get(i).getStarRating() });
						}
					}
				}
			});

			if (e1.getStateChange() == ItemEvent.DESELECTED) {
				actorNameField.setEnabled(false);
				actorNameButton.setEnabled(false);
				birthYear.setEnabled(true);
				SelectAll();
			}
		} else if (e1.getSource() == birthYear) {
			birthYearField.setEnabled(true);
			birthYearButton.setEnabled(true);
			actorName.setEnabled(false);

			birthYearButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == birthYearButton) {
						dao.selectByReleasedYear(birthYearField.getText());

						if (model.getRowCount() > 0) {
							for (int i = model.getRowCount() - 1; i > -1; i--) {
								model.removeRow(i);
							}
						}

						for (int i = 0; i < dao.selectByReleasedYear(birthYearField.getText()).size(); i++) {
							System.out.println("개봉일");
							model.addRow(new Object[] {
									dao.selectByReleasedYear(birthYearField.getText()).get(i).getMovieNumber(),
									dao.selectByReleasedYear(birthYearField.getText()).get(i).getMovieName(),
									dao.selectByReleasedYear(birthYearField.getText()).get(i).getReleasedDate(),
									dao.selectByReleasedYear(birthYearField.getText()).get(i).getRevenue(),
									dao.selectByReleasedYear(birthYearField.getText()).get(i).getAudience(),
									dao.selectByReleasedYear(birthYearField.getText()).get(i).getScreen(),
									dao.selectByReleasedYear(birthYearField.getText()).get(i).getStarRating() });
						}
					}
				}
			});

			if (e1.getStateChange() == ItemEvent.DESELECTED) {
				birthYearField.setEnabled(false);
				birthYearButton.setEnabled(false);
				actorName.setEnabled(true);
				SelectAll();
			}
		}
		
		
		
		
	}
	
	// 내부 클래스
	class CreatePanel extends JFrame {
		
		JPanel panel;

		JLabel label;
		
		
		JTextField actorNumberField;
		JTextField actorNameField;
		JTextField birthYearField;
		JTextField heightField;
		JTextField weightField;
		JTextField spouseField;
		// 여기까지
		
		JLabel movieNumberLabel;
		JLabel movieNameLabel;
		JLabel releasedDateLabel;
		JLabel revenueLabel;
		JLabel audienceLabel;
		JLabel screenLabel;
		JLabel starRatingLabel;
		
		JButton confirmButton;
		
		MovieInfoDto dto;
		
		
		public CreatePanel() {
			
			setBounds(600, 600, 600, 600);
			
			panel = new JPanel();
			setContentPane(panel);
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(null);
			
			label = new JLabel("추가하기");
			label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			label.setBounds(280, 40, 500, 30);
			panel.add(label);
			
			movieNumberLabel = new JLabel("영화번호");
			movieNumberLabel.setBounds(160, 100, 50, 30);
			panel.add(movieNumberLabel);
			
			movieNumberField = new JTextField("123");
			movieNumberField.setBounds(240, 100, 200, 30);
			panel.add(movieNumberField);
			
			movieNameLabel = new JLabel("영화제목");
			movieNameLabel.setBounds(160, 150, 50, 30);
			panel.add(movieNameLabel);
			
			movieNameField = new JTextField("명량");
			movieNameField.setBounds(240, 150, 200, 30);
			panel.add(movieNameField);
			
			
			releasedDateLabel = new JLabel("개봉일");
			releasedDateLabel.setBounds(160, 200, 50, 30);
			panel.add(releasedDateLabel);
			
			releasedDateField = new JTextField("1995-02-02");
			releasedDateField.setBounds(240, 200, 200, 30);
			panel.add(releasedDateField);
			
			revenueLabel = new JLabel("수익금");
			revenueLabel.setBounds(160, 250, 50, 30);
			panel.add(revenueLabel);
			
			revenueField = new JTextField("12341234");
			revenueField.setBounds(240, 250, 200, 30);
			panel.add(revenueField);
			
			audienceLabel = new JLabel("관객 수");
			audienceLabel.setBounds(160, 300, 50, 30);
			panel.add(audienceLabel);
			
			audienceField = new JTextField("123123");
			audienceField.setBounds(240, 300, 200, 30);
			panel.add(audienceField);
			
			screenLabel = new JLabel("스크린 수");
			screenLabel.setBounds(160, 350, 60, 30);
			panel.add(screenLabel);
			
			screenField = new JTextField("1234234");
			screenField.setBounds(240, 350, 200, 30);
			panel.add(screenField);
			
			starRatingLabel = new JLabel("평점");
			starRatingLabel.setBounds(160, 400, 50, 30);
			panel.add(starRatingLabel);
			
			starRatingField = new JTextField("1");
			starRatingField.setBounds(240, 400, 200, 30);
			panel.add(starRatingField);
			
			confirmButton = new JButton("확인");
			confirmButton.setBounds(280, 460, 100, 30);
			panel.add(confirmButton);
			
			setLocationRelativeTo(null);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setVisible(true);
			
		
			
			confirmButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					dto = new MovieInfoDto(Integer.parseInt(movieNumberField.getText()), movieNameField.getText(),
							releasedDateField.getText(), Long.parseLong(revenueField.getText()), Long.parseLong(audienceField.getText()),
							Integer.parseInt(screenField.getText()), Float.parseFloat(starRatingField.getText()));
					
					
					System.out.println("추가 버튼");
					dao.insertData(dto);
					
					
//					if (model.getRowCount() > 0) {
//						for (int i = model.getRowCount() - 1; i > -1; i--) {
//							model.removeRow(i);
//						}
//					}
					
					SelectAll();
					
					setVisible(false);
					
				}
			});
			
		}
	
	
	}
	
	class DeletePanel {
		
		public DeletePanel() {
			
			String movieName = JOptionPane.showInputDialog(null, "삭제할 영화 제목을 입력하세요", null);
			dao.deleteData(movieName);
			
			
			SelectAll();
			
			
		}
		
	}

	class UpdatePanel extends JFrame{
		
		JPanel panel;

		JLabel label;
		
		
		JTextField movieNumberField;
		JTextField movieNameField;
		JTextField releasedDateField;
		JTextField revenueField;
		JTextField audienceField;
		JTextField screenField;
		JTextField starRatingField;
		
		JLabel movieNumberLabel;
		JLabel movieNameLabel;
		JLabel releasedDateLabel;
		JLabel revenueLabel;
		JLabel audienceLabel;
		JLabel screenLabel;
		JLabel starRatingLabel;
		
		JButton confirmButton;
		
		MovieInfoDto dto;
		
		
		public UpdatePanel()  {
			
			
			setBounds(600, 600, 600, 600);
			
			panel = new JPanel();
			setContentPane(panel);
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setLayout(null);
			
			label = new JLabel("수정하기");
			label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			label.setBounds(280, 40, 500, 30);
			panel.add(label);
			
			movieNumberLabel = new JLabel("영화번호");
			movieNumberLabel.setBounds(160, 100, 50, 30);
			panel.add(movieNumberLabel);
			
			movieNumberField = new JTextField("");
			movieNumberField.setBounds(240, 100, 200, 30);
			panel.add(movieNumberField);
			
			movieNameLabel = new JLabel("영화제목");
			movieNameLabel.setBounds(160, 150, 50, 30);
			panel.add(movieNameLabel);
			
			movieNameField = new JTextField("");
			movieNameField.setBounds(240, 150, 200, 30);
			panel.add(movieNameField);
			
			
			releasedDateLabel = new JLabel("개봉일");
			releasedDateLabel.setBounds(160, 200, 50, 30);
			panel.add(releasedDateLabel);
			
			releasedDateField = new JTextField("");
			releasedDateField.setBounds(240, 200, 200, 30);
			panel.add(releasedDateField);
			
			revenueLabel = new JLabel("수익금");
			revenueLabel.setBounds(160, 250, 50, 30);
			panel.add(revenueLabel);
			
			revenueField = new JTextField("");
			revenueField.setBounds(240, 250, 200, 30);
			panel.add(revenueField);
			
			audienceLabel = new JLabel("관객 수");
			audienceLabel.setBounds(160, 300, 50, 30);
			panel.add(audienceLabel);
			
			audienceField = new JTextField("");
			audienceField.setBounds(240, 300, 200, 30);
			panel.add(audienceField);
			
			screenLabel = new JLabel("스크린 수");
			screenLabel.setBounds(160, 350, 60, 30);
			panel.add(screenLabel);
			
			screenField = new JTextField("");
			screenField.setBounds(240, 350, 200, 30);
			panel.add(screenField);
			
			starRatingLabel = new JLabel("평점");
			starRatingLabel.setBounds(160, 400, 50, 30);
			panel.add(starRatingLabel);
			
			starRatingField = new JTextField("");
			starRatingField.setBounds(240, 400, 200, 30);
			panel.add(starRatingField);
			
			confirmButton = new JButton("확인");
			confirmButton.setBounds(280, 460, 100, 30);
			panel.add(confirmButton);
			
			setLocationRelativeTo(null);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setVisible(true);
			
		
			String movieName = JOptionPane.showInputDialog(null, "수정할 영화 제목을 입력하세요", null, JOptionPane.OK_CANCEL_OPTION);
			
			if((movieName != null) && (movieName.length() > 0)) {
				for(int j = 0; j < model.getRowCount(); j++) {
					for(int i = 0; i < model.getColumnCount(); i++) {
						if(model.getValueAt(j, i).equals(movieName)) {
							movieNumberField.setText(String.valueOf(model.getValueAt(j, 0)));
							movieNameField.setText(String.valueOf(model.getValueAt(j, 1)));
							releasedDateField.setText(String.valueOf(model.getValueAt(j, 2)));
							revenueField.setText(String.valueOf(model.getValueAt(j, 3)));
							audienceField.setText(String.valueOf(model.getValueAt(j, 4)));
							screenField.setText(String.valueOf(model.getValueAt(j, 5)));
							starRatingField.setText(String.valueOf(model.getValueAt(j, 6)));
						}
						
					}
				}
				
			}
			
			
//			if((movieName != null) && (movieName.length() > 0)) {
//						if(model.getValueAt(j, i).equals(movieName)) {
//							movieNumberField.setText(String.valueOf(model.getValueAt(j, 0)));
//							movieNameField.setText(String.valueOf(model.getValueAt(j, 1)));
//							releasedDateField.setText(String.valueOf(model.getValueAt(j, 2)));
//							revenueField.setText(String.valueOf(model.getValueAt(j, 3)));
//							audienceField.setText(String.valueOf(model.getValueAt(j, 4)));
//							screenField.setText(String.valueOf(model.getValueAt(j, 5)));
//							starRatingField.setText(String.valueOf(model.getValueAt(j, 6)));
//							break;
//						}
//						
//				
//			}
			
			
			confirmButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
					System.out.println("수정 버튼");
					
//					int temp1 = Integer.parseInt(movieNumberField.getText());
//					String temp2  = movieNameField.getText();
//					String temp3  = releasedDateField.getText();
//					long temp4 = Long.parseLong(revenueField.getText());
//					long temp5 = Long.parseLong(audienceField.getText());
//					int temp6 = Integer.parseInt(screenField.getText());
//					float temp7 = Float.parseFloat(starRatingField.getText());
					
					
//					movieNumberField.setText(temp1 + "");
//					movieNameField.setText(temp2);
//					releasedDateField.setText(temp3);
//					revenueField.setText(temp4 + "");
//					audienceField.setText(temp5 + "");
//					screenField.setText(temp6 + "");
//					starRatingField.setText(temp7 + "");
					
					dao.updateData(Integer.parseInt(movieNumberField.getText()), 
							movieNameField.getText(), 
							releasedDateField.getText(),
							Long.parseLong(revenueField.getText()), 
							Long.parseLong(audienceField.getText()), 
							Integer.parseInt(screenField.getText()), 
							Float.parseFloat(starRatingField.getText()), movieName);
					
					SelectAll();
					
					setVisible(false);
					
				}
			});
		}
		
	}	
	
	
	
}
