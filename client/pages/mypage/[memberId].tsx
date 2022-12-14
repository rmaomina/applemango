import React, { useEffect, useState } from "react";
import UserModify from "../../components/UserInfo";
import DefaultModal from "../../components/modals/DefaultModal";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import GalleryItem from "../../components/mypage/GalleryItem";
import { Toast } from "../../components/util/Toast";
import { getCookie, removeCookies } from "../../components/util/cookie";
import axios, { AxiosResponse } from "axios";
import { useRecoilState } from "recoil";
import { memberIdState } from "../../recoil/memberId";
import Link from "next/link";
import Image from "next/image";

import { useCookies } from "react-cookie";
import { userState } from "../../recoil/user";
import { useRouter } from "next/router";
import { notifyError } from "../../components/util/Toast";
import { getUserInfoFetch } from "../../fetch/userInfo";

const Mypage = () => {
  const [memberId, setMemberId] = useRecoilState(memberIdState);
  const userId = memberId.memberId;
  const [click, setClick] = useState<boolean>(false);
  const [LuckMango, setLuckMango] = useState<object[]>([]);
  const [userName, setUserName] = useState<string>("");
  const [userImg, setUserImg] = useState<any>("");
  const [bgUrl, setBgUrl] = useState<string>("");
  const [modal, setModal] = useState<boolean>(false);
  const [length, setLength] = useState<Number>(0);
  const [cookies] = useCookies(["accessJwtToken"]);
  const [user, setUser] = useRecoilState(userState);
  const route = useRouter();

  const checkLogin = () => {
    const token = cookies.accessJwtToken;
    if (token === undefined || token === "") {
      setUser({ login: false });
      setMemberId({ memberId: 0 });
      localStorage.removeItem("recoil-persist");
      removeCookies("accessJwtToken");
      route.push("/login");
    }
  };

  const userModify = () => {
    setClick(!click);
  };

  const isModal = () => {
    setModal(!modal);
  };

  const getLuckMango = async () => {
    const res = await getUserInfoFetch(
      `/api/luckMango/member?memberId=${userId}&page=1&size=100&sort=desc`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${getCookie("accessJwtToken")}`,
        },
      },
    );
    setLength(res.data?.length);
    setLuckMango(res.data);
  };

  const getUserName = async () => {
    await axios({
      method: "get",
      url: `/api/member/${userId}`,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getCookie("accessJwtToken")}`,
      },
    }).then((res: AxiosResponse) => {
      if (res.statusText === "Unauthorized") {
        notifyError({ message: "???????????? ????????? ??????????????????.", icon: "????" });
        route.push("/login");
      } else if (res.status >= 400) {
        notifyError({
          message: "????????? ???????????? ????????????. \n ?????? ?????? ????????? ?????????.",
          icon: "????",
        });
      }
      setUserName(res.data.data.name);
      setUserImg(res.data.data.imgUrl);
    });
  };

  useEffect(() => {
    getLuckMango();
    getUserName();
    checkLogin();
  }, []);

  return (
    <div>
      <Header />
      <main className="pt-[58px]">
        <div className="flex flex-col w-full h-full min-h-screen px-6 mobile:px-4 mx-auto max-w-[440px]">
          <div className="max-w-[400px] w-full relative flex mt-10">
            <div>
              {userImg.length <= 10 ? (
                <div className="bg-[url(/images/char/profile.webp)] w-36 h-36 relative justify-center mg-border-2 mg-flex bg-center rounded-full bg-cover"></div>
              ) : (
                <div
                  style={{ backgroundImage: `url("${userImg}")` }}
                  className="relative justify-center bg-center bg-cover rounded-full w-36 h-36 mg-border-2 mg-flex"
                ></div>
              )}
            </div>
            <div className="flex flex-col justify-center pl-5">
              <div className="text-3xl">{userName}</div>
              <div className="flex gap-5 text-base">
                <button
                  className="underline text-mono-400 hover:cursor-pointer hover:text-mono-300"
                  onClick={userModify}
                >
                  ????????????
                </button>
                <button
                  className="underline text-mono-400 hover:cursor-pointer hover:text-mono-300"
                  onClick={isModal}
                >
                  ????????????
                </button>
              </div>
            </div>
          </div>
          <div className="flex flex-row col-span-1">
            {click ? (
              <UserModify
                handle={userModify}
                userName={userName}
                modal={modal}
                setBgUrl={setBgUrl}
                bgUrl={bgUrl}
                userImg={userImg}
                setUserImg={setUserImg}
              />
            ) : (
              <div className="flex flex-col w-full mb-5">
                <div className="flex mt-[40px] mb-[10px] text-xl pl-2">
                  ?????? ?????? ?????????
                </div>
                <div className="relative grid w-full justify-items-center mb-[20px] grid-flow-row grid-cols-1 mobile:gap-4 gap-6">
                  {LuckMango.map((el: any, index: number) => (
                    <GalleryItem
                      key={index}
                      luckMangoId={el.luckMangoId}
                      bgImage={el.bgImage}
                      {...el}
                    />
                  ))}
                </div>
              </div>
            )}
          </div>
          {length === 0 && !click ? (
            <div className="flex flex-col items-center w-full">
              <p className="mb-1 text-mono-textDisabled">
                ???? ?????? ????????? ???????????? ????????????.
              </p>
              <Link
                href="/create"
                className="mb-4 selection:underline text-primary-normal hover:text-primary-hover"
              >
                ????????? ???????????? ????????? ??????????
              </Link>
              <Image
                width={113}
                height={95}
                src="/images/char/char-button1.svg"
                alt="?????? ?????? ????????? ?????????"
              />
              <Link href="/create" className="mg-primary-button">
                ????????? ???????????????!
              </Link>
            </div>
          ) : null}
        </div>
        <Toast />
      </main>
      <Footer />
      {modal && <DefaultModal setModal={setModal} />}
    </div>
  );
};

export default Mypage;
