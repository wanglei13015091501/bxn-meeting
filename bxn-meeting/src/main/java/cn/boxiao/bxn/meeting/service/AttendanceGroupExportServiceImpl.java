/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.boxiao.bxn.meeting.MeetingConstants;
import cn.boxiao.bxn.meeting.util.CsvUtils;
import cn.boxiao.bxn.meeting.vo.AttendanceGroupVo;
import cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserStatVo;
import cn.boxiao.bxn.meeting.vo.AttendanceUserVo;

/**
 * @author liumeng
 * @since bxc 1.0 2017年4月24日
 */
@Service
public class AttendanceGroupExportServiceImpl implements AttendanceGroupExportService, MeetingConstants {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AttendanceGroupService attendanceGroupService;

	private static final String ATTENDANCE_GROUP_HEADER = "序号,部门,应到,实到,全勤,迟到,早退,迟到/早退,请假,缺勤,漏卡,未打卡,全勤率";

	private static final String ATTENDANCE_GROUP_DETAIL_HEADER = "状态,教工号/学号,参会人员,签到时间,签退时间,备注";

	private static final String STAT_GROUP_NORMAL_HEADER = "序号,姓名,教工号/学号";

	private static final String STAT_ALL_NORMAL_HEADER = "序号,部门,姓名,教工号/学号";

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupExportService#exportAttendanceGroups(cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo)
	 */
	@Override
	public HSSFWorkbook exportAttendanceGroups(AttendanceMeetingVo attendanceMeetingVo) {
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 创建部门统计sheet
		HSSFSheet sheet = workbook.createSheet("部门考勤统计");
		sheet.setColumnWidth(0, 20 * 256); // 序号
		sheet.setColumnWidth(1, 20 * 256); // 部门
		sheet.setColumnWidth(2, 20 * 256); // 应到
		sheet.setColumnWidth(3, 20 * 256); // 实到
		sheet.setColumnWidth(4, 20 * 256); // 全勤
		sheet.setColumnWidth(5, 20 * 256); // 迟到
		sheet.setColumnWidth(6, 20 * 256); // 早退
		sheet.setColumnWidth(7, 20 * 256); // 迟到/早退
		sheet.setColumnWidth(8, 20 * 256); // 请假
		sheet.setColumnWidth(9, 20 * 256); // 缺勤
		sheet.setColumnWidth(10, 20 * 256); // 漏卡
		sheet.setColumnWidth(11, 20 * 256); // 未打卡
		sheet.setColumnWidth(12, 20 * 256); // 全勤率
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		headStyle.setFillForegroundColor((short) 13);// 设置背景色
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFFont headFont = workbook.createFont();
		headFont.setFontName("黑体");
		headFont.setFontHeightInPoints((short) 14);// 设置字体大小
		headStyle.setFont(headFont);

		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setFontHeightInPoints((short) 12);// 设置字体大小
		bodyStyle.setFont(bodyFont);
		bodyStyle.setWrapText(true);// 自动换行

		List<String> headcellTexts = Lists.newArrayList(CsvUtils.convertCSVToList(ATTENDANCE_GROUP_HEADER));
		Row headRow = sheet.createRow(0);
		for (int i = 0; i < headcellTexts.size(); i++) {
			String text = headcellTexts.get(i);
			Cell cell = headRow.createCell(i);
			cell.setCellValue(text);
			cell.setCellStyle(headStyle);
		}

		List<AttendanceGroupVo> attendanceGroupVoList = attendanceGroupService
				.queryAttendanceGroups(attendanceMeetingVo.getId());
		int rowNum = 1;
		for (AttendanceGroupVo attendanceGroupVo : attendanceGroupVoList) {
			Row valueRow = sheet.createRow(rowNum);
			for (int i = 0; i < headcellTexts.size(); i++) {
				Cell cell = valueRow.createCell(i);
				cell.setCellStyle(bodyStyle);
				if (i == 0) {
					cell.setCellValue(rowNum);
				} else if (i == 1) {
					cell.setCellValue(attendanceGroupVo.getGroupName());
				} else if (i == 2) {
					cell.setCellValue(attendanceGroupVo.getNeedJoinNum());
				} else if (i == 3) {
					cell.setCellValue(attendanceGroupVo.getActualNum());
				} else if (i == 4) {
					cell.setCellValue(attendanceGroupVo.getNormalNum());
				} else if (i == 5) {
					cell.setCellValue(attendanceGroupVo.getLateNum());
				} else if (i == 6) {
					cell.setCellValue(attendanceGroupVo.getLeaveNum());
				} else if (i == 7) {
					cell.setCellValue(attendanceGroupVo.getLeaveLateNum());
				} else if (i == 8) {
					cell.setCellValue(attendanceGroupVo.getHolidayNum());
				} else if (i == 9) {
					cell.setCellValue(attendanceGroupVo.getAbsenceNum());
				} else if (i == 10) {
					cell.setCellValue(attendanceGroupVo.getMissingNum());
				} else if (i == 11) {
					cell.setCellValue(attendanceGroupVo.getUnPunchNum());
				} else if (i == 12) {
					cell.setCellValue(attendanceGroupVo.getNormalRates());
				}
			}
			rowNum++;
		}
		AttendanceGroupVo summaryGroupVo = attendanceGroupService.computeAttendanceGroupSummary(attendanceGroupVoList);
		Row valueRow = sheet.createRow(rowNum);
		for (int i = 0; i < headcellTexts.size(); i++) {
			Cell cell = valueRow.createCell(i);
			cell.setCellStyle(bodyStyle);
			if (i == 0) {
				cell.setCellValue(rowNum);
			} else if (i == 1) {
				cell.setCellValue("总计");
			} else if (i == 2) {
				cell.setCellValue(summaryGroupVo.getNeedJoinNum());
			} else if (i == 3) {
				cell.setCellValue(summaryGroupVo.getActualNum());
			} else if (i == 4) {
				cell.setCellValue(summaryGroupVo.getNormalNum());
			} else if (i == 5) {
				cell.setCellValue(summaryGroupVo.getLateNum());
			} else if (i == 6) {
				cell.setCellValue(summaryGroupVo.getLeaveNum());
			} else if (i == 7) {
				cell.setCellValue(summaryGroupVo.getLeaveLateNum());
			} else if (i == 8) {
				cell.setCellValue(summaryGroupVo.getHolidayNum());
			} else if (i == 9) {
				cell.setCellValue(summaryGroupVo.getAbsenceNum());
			} else if (i == 10) {
				cell.setCellValue(summaryGroupVo.getMissingNum());
			} else if (i == 11) {
				cell.setCellValue(summaryGroupVo.getUnPunchNum());
			} else if (i == 12) {
				cell.setCellValue(summaryGroupVo.getNormalRates());
			}
		}

		// 创建各部门明细Sheet
		for (AttendanceGroupVo attendanceGroupVo : attendanceGroupVoList) {

			// 创建部门统计sheet
			HSSFSheet groupSheet = workbook.createSheet(attendanceGroupVo.getGroupName() + "部门考勤明细");
			groupSheet.setColumnWidth(0, 20 * 256); // 状态
			groupSheet.setColumnWidth(1, 20 * 256); // 教工号/学号
			groupSheet.setColumnWidth(2, 20 * 256); // 参会人员
			groupSheet.setColumnWidth(3, 20 * 256); // 签到时间
			groupSheet.setColumnWidth(4, 20 * 256); // 签退时间
			groupSheet.setColumnWidth(5, 20 * 256); // 备注

			List<String> groupHeadcellTexts = Lists
					.newArrayList(CsvUtils.convertCSVToList(ATTENDANCE_GROUP_DETAIL_HEADER));
			Row groupHeadRow = groupSheet.createRow(0);
			for (int i = 0; i < groupHeadcellTexts.size(); i++) {
				String text = groupHeadcellTexts.get(i);
				Cell cell = groupHeadRow.createCell(i);
				cell.setCellValue(text);
				cell.setCellStyle(headStyle);
			}

			List<AttendanceUserVo> attendanceUserVoList = attendanceGroupService
					.queryAttendanceUsers(attendanceMeetingVo, attendanceGroupVo.getGroupId());
			int groupRowNum = 1;
			for (AttendanceUserVo attendanceUserVo : attendanceUserVoList) {
				Row grouValueRow = groupSheet.createRow(groupRowNum);
				for (int i = 0; i < groupHeadcellTexts.size(); i++) {
					Cell cell = grouValueRow.createCell(i);
					cell.setCellStyle(bodyStyle);
					if (i == 0) {
						cell.setCellValue(attendanceUserVo.getStatusText());
					} else if (i == 1) {
						cell.setCellValue(attendanceUserVo.getUniqueNo());
					} else if (i == 2) {
						cell.setCellValue(attendanceUserVo.getFullName());
					} else if (i == 3) {
						cell.setCellValue(attendanceUserVo.getActualSignTime());
					} else if (i == 4) {
						cell.setCellValue(attendanceUserVo.getActualLogoutTime());
					} else if (i == 5) {
						cell.setCellValue(attendanceUserVo.getDescription());
					}
				}
				groupRowNum++;
			}

		}
		return workbook;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupExportService#exportAttendanceUsers(cn.boxiao.bxn.meeting.vo.AttendanceMeetingVo,
	 *      java.lang.String)
	 */
	@Override
	public HSSFWorkbook exportAttendanceUsers(AttendanceMeetingVo attendanceMeetingVo, String groupId) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("考勤明细");
		sheet.setColumnWidth(0, 20 * 256); // 状态
		sheet.setColumnWidth(1, 20 * 256); // 教工号/学号
		sheet.setColumnWidth(2, 20 * 256); // 参会人员
		sheet.setColumnWidth(3, 20 * 256); // 签到时间
		sheet.setColumnWidth(4, 20 * 256); // 签退时间
		sheet.setColumnWidth(5, 20 * 256); // 备注
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		headStyle.setFillForegroundColor((short) 13);// 设置背景色
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFFont headFont = workbook.createFont();
		headFont.setFontName("黑体");
		headFont.setFontHeightInPoints((short) 14);// 设置字体大小
		headStyle.setFont(headFont);

		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setFontHeightInPoints((short) 12);// 设置字体大小
		bodyStyle.setFont(bodyFont);
		bodyStyle.setWrapText(true);// 自动换行

		List<String> headcellTexts = Lists.newArrayList(CsvUtils.convertCSVToList(ATTENDANCE_GROUP_DETAIL_HEADER));
		Row headRow = sheet.createRow(0);
		for (int i = 0; i < headcellTexts.size(); i++) {
			String text = headcellTexts.get(i);
			Cell cell = headRow.createCell(i);
			cell.setCellValue(text);
			cell.setCellStyle(headStyle);
		}

		List<AttendanceUserVo> attendanceUserVoList = attendanceGroupService.queryAttendanceUsers(attendanceMeetingVo,
				groupId);
		int rowNum = 1;
		for (AttendanceUserVo attendanceUserVo : attendanceUserVoList) {
			Row valueRow = sheet.createRow(rowNum);
			for (int i = 0; i < headcellTexts.size(); i++) {
				Cell cell = valueRow.createCell(i);
				cell.setCellStyle(bodyStyle);
				if (i == 0) {
					cell.setCellValue(attendanceUserVo.getStatusText());
				} else if (i == 1) {
					cell.setCellValue(attendanceUserVo.getUniqueNo());
				} else if (i == 2) {
					cell.setCellValue(attendanceUserVo.getFullName());
				} else if (i == 3) {
					cell.setCellValue(attendanceUserVo.getActualSignTime());
				} else if (i == 4) {
					cell.setCellValue(attendanceUserVo.getActualLogoutTime());
				} else if (i == 5) {
					cell.setCellValue(attendanceUserVo.getDescription());
				}
			}
			rowNum++;
		}
		return workbook;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupExportService#exportAttendanceNormalUsers(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public HSSFWorkbook exportAttendanceNormalUsers(String meetingTypeId, String beginDate, String endDate) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("全勤名单");
		sheet.setColumnWidth(0, 20 * 256); // 序号
		sheet.setColumnWidth(1, 20 * 256); // 部门
		sheet.setColumnWidth(2, 20 * 256); // 姓名
		sheet.setColumnWidth(3, 20 * 256); // 教工号/学号
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		headStyle.setFillForegroundColor((short) 13);// 设置背景色
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFFont headFont = workbook.createFont();
		headFont.setFontName("黑体");
		headFont.setFontHeightInPoints((short) 14);// 设置字体大小
		headStyle.setFont(headFont);

		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setFontHeightInPoints((short) 12);// 设置字体大小
		bodyStyle.setFont(bodyFont);
		bodyStyle.setWrapText(true);// 自动换行

		List<String> headcellTexts = Lists.newArrayList(CsvUtils.convertCSVToList(STAT_ALL_NORMAL_HEADER));
		Row headRow = sheet.createRow(0);
		for (int i = 0; i < headcellTexts.size(); i++) {
			String text = headcellTexts.get(i);
			Cell cell = headRow.createCell(i);
			cell.setCellValue(text);
			cell.setCellStyle(headStyle);
		}

		List<AttendanceUserStatVo> attendanceUserStatVoList = attendanceGroupService
				.queryAttendanceNormalUsers(meetingTypeId, beginDate, endDate);
		int rowNum = 1;
		for (AttendanceUserStatVo attendanceUserStatVo : attendanceUserStatVoList) {
			Row valueRow = sheet.createRow(rowNum);
			for (int i = 0; i < headcellTexts.size(); i++) {
				Cell cell = valueRow.createCell(i);
				cell.setCellStyle(bodyStyle);
				if (i == 0) {
					cell.setCellValue(rowNum);
				} else if (i == 1) {
					cell.setCellValue(attendanceUserStatVo.getOrganizationName());
				} else if (i == 2) {
					cell.setCellValue(attendanceUserStatVo.getFullName());
				} else if (i == 3) {
					cell.setCellValue(attendanceUserStatVo.getUniqueNo());
				}
			}
			rowNum++;
		}
		return workbook;
	}

	/**
	 * @see cn.boxiao.bxn.meeting.service.AttendanceGroupExportService#exportAttendanceNormalUsers(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public HSSFWorkbook exportAttendanceNormalUsers(String meetingTypeId, String groupId, String beginDate,
			String endDate) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("全勤名单");
		sheet.setColumnWidth(0, 20 * 256); // 序号
		sheet.setColumnWidth(1, 20 * 256); // 姓名
		sheet.setColumnWidth(2, 20 * 256); // 教工号/学号
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		headStyle.setFillForegroundColor((short) 13);// 设置背景色
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFFont headFont = workbook.createFont();
		headFont.setFontName("黑体");
		headFont.setFontHeightInPoints((short) 14);// 设置字体大小
		headStyle.setFont(headFont);

		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setFontHeightInPoints((short) 12);// 设置字体大小
		bodyStyle.setFont(bodyFont);
		bodyStyle.setWrapText(true);// 自动换行

		List<String> headcellTexts = Lists.newArrayList(CsvUtils.convertCSVToList(STAT_GROUP_NORMAL_HEADER));
		Row headRow = sheet.createRow(0);
		for (int i = 0; i < headcellTexts.size(); i++) {
			String text = headcellTexts.get(i);
			Cell cell = headRow.createCell(i);
			cell.setCellValue(text);
			cell.setCellStyle(headStyle);
		}

		List<AttendanceUserStatVo> attendanceUserStatVoList = attendanceGroupService
				.queryAttendanceNormalUsers(meetingTypeId, groupId, beginDate, endDate);
		int rowNum = 1;
		for (AttendanceUserStatVo attendanceUserStatVo : attendanceUserStatVoList) {
			Row valueRow = sheet.createRow(rowNum);
			for (int i = 0; i < headcellTexts.size(); i++) {
				Cell cell = valueRow.createCell(i);
				cell.setCellStyle(bodyStyle);
				if (i == 0) {
					cell.setCellValue(rowNum);
				} else if (i == 1) {
					cell.setCellValue(attendanceUserStatVo.getFullName());
				} else if (i == 2) {
					cell.setCellValue(attendanceUserStatVo.getUniqueNo());
				}
			}
			rowNum++;
		}
		return workbook;
	}

}
