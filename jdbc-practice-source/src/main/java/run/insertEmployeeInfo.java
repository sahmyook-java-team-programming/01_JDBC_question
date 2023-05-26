package run;

import model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class insertEmployeeInfo {

    // 사원 정보 전체를 입력받아 DTO객체에 담아서 insert
    // insert 성공하면 "직원 등록에 성공하였습니다." 출력
    // insert 실패하면 "직원 등록에 실패하였습니다." 출력

    public static void main(String[] args) {
        Connection con = getConnection();

        PreparedStatement pstat = null;
        int result = 0;
        EmployeeDTO empDTO = new EmployeeDTO();

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));
            String query = prop.getProperty("insertEmp");

            Scanner sc = new Scanner(System.in);
            System.out.print("등록하실 사원번호를 입력해주세요 : ");
            String empId = sc.nextLine();

            System.out.print("등록하실 사원의 이름을 입력해주세요 : ");
            String empName = sc.nextLine();

            System.out.print("등록하실 사원의 주민번호를 입력해주세요 : ");
            String empNo = sc.nextLine();

            System.out.print("등록하실 사원의 이메일을 입력해주세요 : ");
            String email = sc.nextLine();

            System.out.print("등록하실 사원의 전화번호를 입력해주세요 : ");
            String phone = sc.nextLine();

            System.out.print("등록하실 사원의 부서번호를 입력해주세요 : ");
            String deptCode = sc.nextLine();

            System.out.print("등록하실 사원의 직급코드를 입력해주세요 : ");
            String jobCode = sc.nextLine();

            System.out.print("등록하실 사원의 급여등급을 입력해주세요 : ");
            String salLevel = sc.nextLine();

            System.out.print("등록하실 사원의 급여를 입력해주세요 : ");
            int salary = sc.nextInt();

            System.out.print("등록하실 사원의 보너스를 입력해주세요 : ");
            double bonus = sc.nextDouble();
            sc.nextLine();

            System.out.print("등록하실 사원의 관리사번을 입력해주세요 : ");
            String managerId = sc.nextLine();

            System.out.print("등록하실 사원의 입사일을 입력해주세요 : ");
            Date hireDate = Date.valueOf(sc.nextLine());

            System.out.print("등록하실 사원의 퇴직여부를 입력해주세요 : ");
            String entYn = sc.nextLine();

            empDTO.setEmpId(empId);
            empDTO.setEmpName(empName);
            empDTO.setEmpNo(empNo);
            empDTO.setEmail(email);
            empDTO.setPhone(phone);
            empDTO.setDeptCode(deptCode);
            empDTO.setJobCode(jobCode);
            empDTO.setSalLevel(salLevel);
            empDTO.setSalary(salary);
            empDTO.setBonus(bonus);
            empDTO.setManagerId(managerId);
            empDTO.setHireDate(hireDate);
            empDTO.setEntYn(entYn);

            pstat = con.prepareStatement(query);
            pstat.setString(1, empDTO.getEmpId());
            pstat.setString(2, empDTO.getEmpName());
            pstat.setString(3, empDTO.getEmpNo());
            pstat.setString(4, empDTO.getEmail());
            pstat.setString(5, empDTO.getPhone());
            pstat.setString(6, empDTO.getDeptCode());
            pstat.setString(7, empDTO.getJobCode());
            pstat.setString(8, empDTO.getSalLevel());
            pstat.setInt(9, empDTO.getSalary());
            pstat.setDouble(10, empDTO.getBonus());
            pstat.setString(11, empDTO.getManagerId());
            pstat.setDate(12, empDTO.getHireDate());
            pstat.setString(13, empDTO.getEntYn());

            result = pstat.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
            close(pstat);
        }

        if(result > 0) {
            System.out.println("직원 등록에 성공하셨습니다.");
        } else {
            System.out.println("직원 등록에 성공하셨습니다.");
        }

    }

}
