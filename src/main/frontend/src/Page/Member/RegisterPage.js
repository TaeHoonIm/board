import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";

import classes from "./RegisterPage.module.css";

import {Button,Col,Row,Form,ToggleButton,ToggleButtonGroup} from "react-bootstrap/";
import FormGroup from "react-bootstrap/esm/FormGroup";

export default function RegisterPage() {
  const navigate = useNavigate(); // 로그인 성공 후에 넘어갈 navigate를 위한 코드

  const [show, setShow] = useState(false);
  const [message, setMessage] = useState();
  const [navigateType , setNavigateType] = useState();

  const [userForm, setUserForm] = useState({
    // 서버로 보내는 데이터
    email: "defaultEmail",
    password: "defaultPassword",
    name: "defaultName",
    nickname: "defaultNickName",
    birth: "defaultBirth",
    sex: 0,
  });
  const [guideMessage, setGuideMessage] = useState({
    // 가이드 문구
    guideEmailText: "",
    guidePwdText: "숫자, 알파벳과 특수문자를 섞어서 8~12자로 작성해주세요",
    guideEqualPwdText: "",
    guideNicknameText : ""
  });

  const [validForm, setValidForm] = useState({
    // 회원가입 유효성 판단
    verifyText: false, // 서버로부터 받은 인증번호를 저장할 state
    verifyNickname: false, // 닉네임 인증 여부
    verifyEqualPwd: false, // 비밀번호 일치 여부
    verifyPwd: false, // 비밀번호 규칙 적합 여부
  });

  function formChangeHandler(event) {
    // 회원가입 폼의 값이 바뀔 때마다 update
    const { value, name: inputValue } = event.target;
    setUserForm({
      ...userForm,
      [inputValue]: event.target.value.trim(),
    });
  }
  function genderClickHandler(value) {
    setUserForm({
      ...userForm,
      sex: value,
    });
  }

  function validEmailHandler() {

  }
  function validNicknameHandler() {

  }

  function submitHandler(event) {

  }

  function pwdChangeHanlder(event) {

  }

  function equalPwdChangeHandler(event) {

  }


  return (
    <>
      <div style={{ width: "100%", height: "6rem" }} />
      <div className={classes['register-title']}>회원가입</div>
      <div className={classes.wrapper}>
        <Form onSubmit={submitHandler}>
          <Form.Group className="mb-3" controlId="formGridCheckEmail">
            <Form.Label>아이디 생성</Form.Label>
            <Form.Control type="email" name="email" onChange={formChangeHandler} placeholder="이메일" className={classes.inputBox} />
            <Form.Text>{guideMessage.guideEmailText}</Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formVerify">
            <Row className="mb-3">
              <Col>
                <Form.Control type="text" placeholder="인증번호" name="verifyText" className={classes.inputBox} />
              </Col>
              <Col xs={3} className={classes["input-wrapper"]}>
                <Button className={classes["verify-button"]} type="button" onClick={validEmailHandler}>
                  이메일발송
                </Button>
              </Col>
            </Row>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formPwd">
            <Form.Control type="password" name="password" onChange={pwdChangeHanlder} placeholder="비밀번호" className={classes.inputBox} />
            <Form.Text>{guideMessage.guidePwdText}</Form.Text>
          </Form.Group>
          <Form.Group className="mb-3" controlId="formCheckPwd">
            <Form.Control type="password" name="checkPassword" onChange={equalPwdChangeHandler} placeholder="비밀번호 확인" className={classes.inputBox} />
            <Form.Text>{guideMessage.guideEqualPwdText}</Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formGridName">
            <Row>
              <Form.Label>개인정보</Form.Label>
              <Col>
                <Form.Control name="name" onChange={formChangeHandler} placeholder="이름" className={classes.inputBox} />
              </Col>

              <Col xs={3} className={classes["input-wrapper"]}>
                <ToggleButtonGroup type="radio" name="options" defaultValue={1}>
                  <ToggleButton className={userForm.sex ? classes.gender : classes["gender-focus"]} id="tbg-radio-1" value={1} onClick={() => genderClickHandler(0)}>
                    남성
                  </ToggleButton>
                  <ToggleButton className={userForm.sex ? classes["gender-focus"] : classes.gender} id="tbg-radio-2" value={2} onClick={() => genderClickHandler(1)}>
                    여성
                  </ToggleButton>
                </ToggleButtonGroup>
              </Col>
            </Row>
          </Form.Group>
          <Form.Group className="mb-3" controlId="formGridNickname">
            <Row>
              <Col>
                <Form.Control type="text" name="nickname" onChange={formChangeHandler} placeholder="닉네임" className={classes.inputBox}/>
              </Col>
              <Col xs={3} className={classes["input-wrapper"]}>
                <Button className={classes["verify-button"]} type="button" name="verifyNickname" onClick={validNicknameHandler}>
                  중복확인
                </Button>
              </Col>
            </Row>
          </Form.Group>
          <FormGroup>
            <Row className="mb-3">
              <Form.Group as={Col} controlId="formBirth">
                <Form.Control type="number" name="birth" onChange={formChangeHandler} placeholder="YYMMDD" className={classes.inputBox}/>
              </Form.Group>
            </Row>
          </FormGroup>

          <Button type="submit" className={classes["submit"]}>
            회원가입 완료
          </Button>
        </Form>
      </div>
    </>
  );
}
