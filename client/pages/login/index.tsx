import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import {
  getCookie,
  setCookie,
  removeCookies,
} from "../../components/util/cookie";
import { notifyError, notifySuccess, Toast } from "../../components/util/Toast";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/user";
import { memberIdState } from "../../recoil/memberId";
import { useCookies } from "react-cookie";

const Login = () => {
  const [password, setPassword] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [isRemember, setIsRemember] = useState<boolean>(false);
  const [user, setUser] = useRecoilState(userState);
  const [memberId, setMemberId] = useRecoilState(memberIdState);
  const router = useRouter();
  const [cookies] = useCookies(["rememberEmail"]);

  const pageChange = () => {
    setTimeout(() => router.push("/"), 1500);
  };

  const onSubmit2 = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios
        .post(
          "api/auth/login",
          {
            username: email,
            password: password,
          },
          {
            withCredentials: true,
          },
        )
        .then(res => {
          const expires = new Date();
          expires.setMinutes(expires.getMinutes() + 60);
          const jwtToken = res.headers.authorization;
          if (jwtToken) {
            setCookie("accessJwtToken", jwtToken, {
              path: "/",
              expires,
            });
          }
          axios({
            method: "get",
            url: "/api/member?page=1&size=100",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${getCookie("accessJwtToken")}`,
            },
          }).then(res =>
            res.data.data.map((el: any) =>
              el.email === email
                ? setMemberId({ memberId: el.memberId })
                : null,
            ),
          );
          setUser({ login: true });
          notifySuccess({
            message: "???????????? ???????????????. \n???????????? ?????? ?????? ?????????!",
            icon: "????",
          });
          pageChange();
        });
    } catch (error) {
      notifyError({
        message: "???????????? ???????????????. \n????????? ?????? ??????????????????!",
        icon: "????",
      });
    }
  };

  const onChangeEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    const emailCurrent = e.target.value;
    setEmail(emailCurrent);
  };

  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    const passwordCurrent = e.target.value;
    setPassword(passwordCurrent);
  };

  const handleOnChange = (e: any) => {
    setIsRemember(e.target.checked);
    const expires = new Date();
    expires.setMinutes(expires.getMinutes() + 60);
    if (e.target.checked) {
      setCookie("rememberEmail", email, {
        expires,
      });
    } else {
      removeCookies("rememberEmail");
    }
  };

  useEffect(() => {
    if (cookies.rememberEmail !== undefined) {
      setEmail(cookies.rememberEmail);
      setIsRemember(true);
    }
  }, []);

  return (
    <div>
      <Header />
      <div className="flex flex-col items-center w-full h-full min-h-screen">
        <div className="flex flex-col max-w-[360px] justify-center text-center mb-10">
          <div className="mt-[100px] mb-[10px] text-4xl">?????????</div>
          <div className="px-[20px]">
            <div className="flex flex-col items-center">
              <p className="mb-4 w-[165px] h-[209px] animate-[welcome_2.5s_steps(7)_infinite] bg-[url(/images/char/sprite.png)]"></p>
              <p className="mb-2 text-xl text-center text-medium">
                ?????? ????????? ?????? ????????????!
              </p>
              <p className="text-base text-center text-medium">
                ????????? ????????? ????????????{" "}
                <span className="text-primary-normal">?????? ?????????</span>?????????
              </p>
            </div>
            <form onSubmit={onSubmit2}>
              <div className="mt-10">
                <label htmlFor="email" className="text-left mg-default-label">
                  ?????????
                </label>
                <input
                  id="email"
                  type="text"
                  placeholder="???????????? ????????? ?????????"
                  className="w-full mg-default-input"
                  onChange={onChangeEmail}
                  defaultValue={email || undefined}
                />
              </div>
              <div className="mt-3">
                <label
                  htmlFor="password"
                  className="text-left mg-default-label"
                >
                  ????????????
                </label>
                <input
                  id="password"
                  type="password"
                  placeholder="??????????????? ????????? ?????????"
                  className="w-full mg-default-input"
                  onChange={onChangePassword}
                />
              </div>
              <button className="mg-primary-button mt-8 w-[320px]">
                ?????????
              </button>
            </form>
            <div className="flex justify-between">
              <div className="flex mt-5">
                <input
                  type="checkbox"
                  className="mr-1"
                  onChange={e => handleOnChange(e)}
                  checked={isRemember}
                />
                <label>ID ??????</label>
              </div>
              <div>
                <Link href="/PwFind">
                  <button className="px-3 mt-5 font-medium underline cursor-pointer text-primary-normal">
                    ???????????? ??????
                  </button>
                </Link>
                <Link href="signup">
                  <button className="pl-1 mt-5 font-medium underline cursor-pointer text-primary-normal">
                    ????????????
                  </button>
                </Link>
              </div>
            </div>
          </div>
        </div>
        <Toast />
      </div>
      <Footer />
    </div>
  );
};

export default Login;
