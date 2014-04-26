sbt "; project js ; optimizeJS"

for file in index-dev.html index.html style.css images/buddha01.svg images/fish.svg images/fish02.svg images/fukusuke1.svg images/fukusuke2.svg images/fukusuke3.svg images/illi-ichi_kanji.svg images/illi-ichi_logo.svg images/lambda-cube.svg images/menu.svg images/name.svg images/nami.svg images/tooltip.svg target/scala-2.10/js-opt.js; do
    cp js/$file ../illi-ichi.github.io/$file
done

cp js/images/favicon.ico ../illi-ichi.github.io/favicon.ico

hash=$(cat .git/refs/heads/master)

cd ../illi-ichi.github.io
git add .
git commit -m $hash

cd -
