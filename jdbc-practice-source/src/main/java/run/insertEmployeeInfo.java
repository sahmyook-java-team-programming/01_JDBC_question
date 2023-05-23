package run;

import model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        PreparedStatement pstmt = null;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        int result = 0;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));
            String query = prop.getProperty("insertMenu");

            Scanner sc = new Scanner(System.in);
            System.out.print("사원번호를 입력해주세요 : ");
            String empId = sc.nextLine();
            System.out.print("사원의 이름을 입력해주세요 : ");
            String empName = sc.nextLine();
            System.out.print("사원의 주민등록번호를 입력해주세요 : ");
            String empNo = sc.nextLine();
            System.out.print("사원의 이메일을 입력해주세요 : ");
            String empEmail = sc.nextLine();
            System.out.print("사원의 전화번호를 입력해주세요 (-제외) : ");
            String empPhone = sc.nextLine();
            System.out.print("사원의 부서코드를 입력해주세요 : ");
            String empDeptCode = sc.nextLine();
            System.out.print("사원의 직급코드를 입력해주세요 : ");
            String empJobCode = sc.nextLine();
            System.out.print("사원의 급여등급을 입력해주세요 : ");
            String empSalLevel = sc.nextLine();
            System.out.print("사원의 급여를 입력해주세요 : ");
            int empSalary = sc.nextInt();
            sc.nextLine();
            System.out.print("사원의 보너스를 입력해주세요 : ");
            float empBonus = sc.nextFloat();
            sc.nextLine();
            System.out.print("사원의 관리자사번을 입력해주세요 : ");
            String empManagerId = sc.nextLine();
            System.out.print("사원의 입사일을 입력해주세요 : ");
            Date empHireDate = Date.valueOf(sc.nextLine());
            System.out.print("사원의 퇴사여부 입력해주세요(Y/N) : ");
            String empEntYn = sc.nextLine();


            employeeDTO.setEmpId(empId);
            employeeDTO.setEmpName(empName);
            employeeDTO.setEmpNO(empNo);
            employeeDTO.setEmail(empEmail);
            employeeDTO.setPhone(empPhone);
            employeeDTO.setDeptCode(empDeptCode);
            employeeDTO.setJobCode(empJobCode);
            employeeDTO.setSalLevel(empSalLevel);
            employeeDTO.setSalary(empSalary);
            employeeDTO.setBonus(empBonus);
            employeeDTO.setManagerId(empManagerId);
            employeeDTO.setHireDate(empHireDate);
            employeeDTO.setEntYn(empEntYn);

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, employeeDTO.getEmpId());
            pstmt.setString(2, employeeDTO.getEmpName());
            pstmt.setString(3, employeeDTO.getEmpNO());
            pstmt.setString(4, employeeDTO.getEmail());
            pstmt.setString(5, employeeDTO.getPhone());
            pstmt.setString(6, employeeDTO.getDeptCode());
            pstmt.setString(7, employeeDTO.getJobCode());
            pstmt.setString(8, employeeDTO.getSalLevel());
            pstmt.setInt(9, employeeDTO.getSalary());
            pstmt.setDouble(10, employeeDTO.getBonus());
            pstmt.setString(11, employeeDTO.getManagerId());
            pstmt.setString(12, String.valueOf(employeeDTO.getHireDate()));
            pstmt.setString(13, employeeDTO.getEntYn());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(con);
            close(pstmt);
        }

        if (result > 0) {
            System.out.println("직원등록에 성공하셨습니다.");
            System.out.println("result = " + result);
        } else {
            System.out.println("직원등록에 실패하셨습니다.");
            System.out.println("result = " + result);
        }

    }
}
