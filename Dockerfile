FROM postgres:17

RUN apt-get update && apt-get install -y locales \
  && sed -i '/ko_KR.UTF-8/s/^# //g' /etc/locale.gen \
  && locale-gen

ENV LANG=ko_KR.UTF-8
ENV LANGUAGE=ko_KR:ko
ENV LC_ALL=ko_KR.UTF-8
