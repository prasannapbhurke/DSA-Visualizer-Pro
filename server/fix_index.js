const fs = require('fs');

const lines = fs.readFileSync('index.js', 'utf8').split('\n');

let startIndex = -1;
let endIndex = -1;

for (let i = 0; i < lines.length; i++) {
  if (lines[i].includes('activeClasses,') && lines[i+1].includes('totalResources: classResources.length,')) {
    startIndex = i;
    break;
  }
}

for (let i = startIndex; i < lines.length; i++) {
  if (lines[i].includes('const persistGoogleUser = async ({ googleId, email, name }) => {')) {
    endIndex = i;
    break;
  }
}

if (startIndex !== -1 && endIndex !== -1) {
  lines.splice(startIndex, endIndex - startIndex);
  fs.writeFileSync('index.js', lines.join('\n'), 'utf8');
  console.log('Deleted ' + (endIndex - startIndex) + ' lines');
} else {
  console.log('Could not find boundaries');
}
